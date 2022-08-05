package com.pentyugov.wflowappbot.application.service.impl;

import com.pentyugov.wflowappbot.application.ApplicationConstants;
import com.pentyugov.wflowappbot.application.rest.payload.request.LoginRequest;
import com.pentyugov.wflowappbot.application.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.pentyugov.wflowappbot.application.ApplicationConstants.API_AUTH_URL;
import static com.pentyugov.wflowappbot.application.ApplicationConstants.API_CHECK_CONNECTION;
import static com.pentyugov.wflowappbot.application.ApplicationConstants.Security.JWT_TOKEN_HEADER;

@Service(SessionService.NAME)
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.password}")
    private String password;

    private Boolean connected = Boolean.FALSE;
    private String jwtToken = "";
    private String sessionId = "";

    private final RestTemplate restTemplate;

    @Override
    public void authenticate() {
        LoginRequest request = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();

        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    API_AUTH_URL,
                    HttpMethod.POST,
                    new HttpEntity<>(request, new HttpHeaders()),
                    Object.class,
                    Collections.emptyMap()
            );
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                List<String> jwtHeader = response.getHeaders().get(JWT_TOKEN_HEADER);
                if (!CollectionUtils.isEmpty(jwtHeader)) {
                    jwtToken = jwtHeader.get(0);
                    setConnected(Boolean.TRUE);
                    logger.info("Successfully connected to server...");
                }
            }
        } catch (HttpStatusCodeException e) {
            logger.error(e.getMessage());
        } catch (ResourceAccessException e) {
            logger.error("Unable connect to server...");
        }
    }

    @Override
    public Boolean isConnectedToServer() {
        return connected;
    }

    @Override
    public Boolean checkConnection() {
        try {
            setConnected(Boolean.FALSE);
            HttpHeaders headers = getJwtHeaders();
            headers.add("session-id", sessionId);
            ResponseEntity<Object> response = restTemplate.exchange(
                    API_CHECK_CONNECTION,
                    HttpMethod.POST,
                    new HttpEntity<>(null, headers),
                    Object.class,
                    Collections.emptyMap()
            );
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                setConnected(Boolean.TRUE);
            }
        } catch (HttpStatusCodeException | ResourceAccessException e) {
            logger.error(e.getMessage());
        }

        return connected;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public HttpHeaders getJwtHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, jwtToken);
        return httpHeaders;
    }

    private void setConnected(Boolean connected) {
        this.connected = connected;
    }
}
