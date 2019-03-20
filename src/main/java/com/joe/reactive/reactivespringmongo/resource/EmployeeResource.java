package com.joe.reactive.reactivespringmongo.resource;

import com.joe.reactive.reactivespringmongo.repository.EmployeeRespository;
import com.joe.reactive.reactivespringmongo.resource.model.Employee;
import com.joe.reactive.reactivespringmongo.resource.model.EmployeeEvent;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeResource {

    private EmployeeRespository employeeRespository;

    public EmployeeResource(EmployeeRespository employeeRespository) {
        this.employeeRespository = employeeRespository;
    }

    @GetMapping("/all")
    public Flux<Employee> getAllEmployees(){
        return employeeRespository.findAll();

    }

    @GetMapping("/{id}")
    public Mono<Employee> getEmployee(@PathVariable("id") String id){
        return employeeRespository.findById(id);
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeEvent> getEvents(@PathVariable("id") String id){
        return employeeRespository.findById(id)
                .flatMapMany(emp->{
                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
                    Flux<EmployeeEvent> employeeEventFlux =
                            Flux.fromStream(()->{
                                return Stream.generate(()->{
                                    return new EmployeeEvent(emp,new Date());
                                });
                            });
                    return Flux.zip(interval,employeeEventFlux)
                            .map(objects -> objects.getT2());
                });

    }
}
