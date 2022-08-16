package com.pentyugov.wflow.telbot.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSettings {
    private Boolean subscribeOnTasks = Boolean.FALSE;
    private Boolean subscribeOnCalendar = Boolean.FALSE;
}