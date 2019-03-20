package com.joe.reactive.reactivespringmongo.repository;

import com.joe.reactive.reactivespringmongo.resource.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface EmployeeRespository extends ReactiveMongoRepository<Employee, String> {
}
