package com.joe.reactive.reactivespringmongo;

import com.joe.reactive.reactivespringmongo.repository.EmployeeRespository;
import com.joe.reactive.reactivespringmongo.resource.model.Employee;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveSpringMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveSpringMongoApplication.class, args);
	}


	@Bean
	CommandLineRunner employees(EmployeeRespository employeeRespository){
		return args -> {
			employeeRespository.deleteAll()
			.subscribe(null, null,
					()->{
						Employee e1= new Employee(UUID.randomUUID().toString(),"Joseph",25000);
						Employee e2= new Employee(UUID.randomUUID().toString(),"JOhn",21000);
						Employee e3= new Employee(UUID.randomUUID().toString(),"Don",5000);
						Employee e4= new Employee(UUID.randomUUID().toString(),"Jeane",1000);
						Employee e5= new Employee(UUID.randomUUID().toString(),"Dane",55000);
						Stream.of(e1,e2,e3,e4,e5)
								.forEach(e->{
									employeeRespository.save(e)
											.subscribe(System.out::println);
								});

					});

		};
	}

}
