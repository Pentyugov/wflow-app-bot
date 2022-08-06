package com.pentyugov.wflowappbot.application.rest.payload.request;

import com.pentyugov.wflowappbot.application.model.UserSettings;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TelbotUpdateUserSettingsRequest {
    Long telUserId;
    UserSettings userSettings;
}
