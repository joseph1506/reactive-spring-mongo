package com.joe.reactive.reactivespringmongo.resource.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document
public class Employee {

    @Id
    private String id;
    private String name;
    private long salary;


}
