package com.pentyugov.wflowappbot.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WflowTask {

    private String number;
    private String description;
    private String priority;
    private String project;
    private Date dueDate;
}
