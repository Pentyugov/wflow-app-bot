package com.pentyugov.wflow.telbot.application.web.rest.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelbotGetTaskPageRequest {
    Long telUserId;
    Integer page;
    String sortBy;

}

