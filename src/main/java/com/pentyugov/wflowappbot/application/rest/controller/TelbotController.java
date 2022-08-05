package com.pentyugov.wflowappbot.application.rest.controller;

import com.pentyugov.wflowappbot.application.service.SessionService;
import com.pentyugov.wflowappbot.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telbot")
@RequiredArgsConstructor
public class TelbotController {

    private final SessionService sessionService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(TelbotController.class);

    @PostMapping("/server-started")
    public ResponseEntity<Object> receiveStartUpNotification(@RequestHeader Map<String, String> headers) {
        String sessionHeader = headers.get("session-id");
        if (StringUtils.hasText(sessionHeader)) {
            try {
                UUID sessionId = UUID.fromString(sessionHeader);
                sessionService.authenticate();
                if (sessionService.isConnectedToServer()) {
                    userService.loadLoggedUsers();
                    return ResponseEntity
                            .ok()
                            .header("session-id", sessionId.toString())
                            .body(null);
                }

            } catch (IllegalArgumentException e) {
                logger.error("Invalid session id...");
            }
        }
        return ResponseEntity.badRequest().body("Invalid server request...");
    }
}
