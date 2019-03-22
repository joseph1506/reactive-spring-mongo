package com.joe.reactive.reactivespringmongo;

import com.joe.reactive.reactivespringmongo.repository.EmployeeRespository;
import com.joe.reactive.reactivespringmongo.resource.model.Employee;
import com.joe.reactive.reactivespringmongo.resource.model.EmployeeEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class RouterHandlers {

    EmployeeRespository employeeRespository;

    public RouterHandlers(EmployeeRespository employeeRespository) {
        this.employeeRespository = employeeRespository;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
            return ServerResponse.ok().body(employeeRespository.findAll(),Employee.class);
    }

    public Mono<ServerResponse> getId(ServerRequest request) {
        return ServerResponse.ok().body(employeeRespository.findById(request.pathVariable("id")), Employee.class);
    }

    public Mono<ServerResponse> getEvents(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(employeeRespository.findById(request.pathVariable("id"))
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
                        }),  EmployeeEvent.class);
    }
}
