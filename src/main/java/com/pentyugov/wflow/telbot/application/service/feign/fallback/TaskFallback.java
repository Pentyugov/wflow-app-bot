package com.pentyugov.wflow.telbot.application.service.feign.fallback;

import com.pentyugov.wflow.telbot.application.service.feign.FeignTaskService;
import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotGetTaskPageRequest;
import com.pentyugov.wflow.telbot.application.web.rest.payload.response.TelbotGetTaskPageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskFallback implements FeignTaskService {
    @Override
    public ResponseEntity<TelbotGetTaskPageResponse> getTaskPage(HttpHeaders headers, TelbotGetTaskPageRequest request) {
        return ResponseEntity.badRequest().build();
    }
}
