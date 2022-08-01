package com.pentyugov.wflowappbot.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class WflowUser {

    private UUID userId;
    private Long telUserId;
    private Long telChatId;
    private UserSettings userSettings;

}
