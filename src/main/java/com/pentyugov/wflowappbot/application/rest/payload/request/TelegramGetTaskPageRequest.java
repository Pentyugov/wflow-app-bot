package com.pentyugov.wflowappbot.application.rest.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramGetTaskPageRequest {
    Long telUserId;
    Integer page;
    String sortBy;

}
