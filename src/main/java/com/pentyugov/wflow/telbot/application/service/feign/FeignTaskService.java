package com.pentyugov.wflow.telbot.application.service.feign;

import com.pentyugov.wflow.telbot.application.service.feign.fallback.TaskFallback;
import com.pentyugov.wflow.telbot.application.web.rest.payload.request.TelbotGetTaskPageRequest;
import com.pentyugov.wflow.telbot.application.web.rest.payload.response.TelbotGetTaskPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "wflow-main-service", fallback = TaskFallback.class)
@Service("FeignTaskService")
public interface FeignTaskService {

    @PostMapping("/api/v1/telbot/tasks/page")
    ResponseEntity<TelbotGetTaskPageResponse> getTaskPage(@RequestHeader HttpHeaders headers,  @RequestBody TelbotGetTaskPageRequest request);

}
