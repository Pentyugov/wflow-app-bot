package com.pentyugov.wflow.telbot.application.web.rest.payload.response;

import com.pentyugov.wflow.telbot.application.model.WflowUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TelbotLoggedUsersResponse {
    private HttpStatus httpStatus;
    private List<WflowUser> users;
}
