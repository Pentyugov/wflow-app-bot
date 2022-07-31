package com.pentyugov.wflowappbot.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WflowTask {

    public static final String PRIORITY_LOW = "PRIORITY$LOW";
    public static final String PRIORITY_MEDIUM = "PRIORITY$MEDIUM";
    public static final String PRIORITY_HIGH = "PRIORITY$HIGH";

    private String number;
    private String description;
    private String priority;
    private String project;
    private Date dueDate;
}
