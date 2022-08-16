package com.pentyugov.wflow.telbot.application.web.rest.payload.request;

import com.pentyugov.wflow.telbot.application.model.UserSettings;
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
