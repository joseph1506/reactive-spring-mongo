package com.joe.reactive.reactivespringmongo.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent {
    private Employee employee;
    private Date date;
}
