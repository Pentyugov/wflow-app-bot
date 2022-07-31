package com.pentyugov.wflowappbot.application.rest.payload.response;

import com.pentyugov.wflowappbot.application.model.WflowUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TelegramLoggedUsersResponse {
    private HttpStatus httpStatus;
    private List<WflowUser> users;
}
