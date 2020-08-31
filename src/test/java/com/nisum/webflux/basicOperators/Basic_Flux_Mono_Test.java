package com.nisum.webflux.basicOperators;
import  org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Arrays;
import java.util.List;
public class Basic_Flux_Mono_Test {
    /**
     * flux with error
     *
     * public final Disposable subscribe(
     *                        @Nullable Consumer<? super T> consumer,
     *            @Nullable Consumer<? super Throwable> errorConsumer,
     *            @Nullable Runnable completeConsumer)
     *
     * Subscribe method is having cunsumer ,erroCunsumer, and completeConsumer, and one more field subscription
     * is there;
     * It returns Disposable:It means the spacified flux can be cancelled or disposed
     */
    @Test
    public void fluxTest() {

        Flux<String> stringFlux = Flux.just("V", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After Error"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is -> " + e)
                        , () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithoutError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();


    }

    @Test
    public void fluxTestElements_WithError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();


    }

    /**
     *
     */
    @Test
    public void fluxTestElements_WithError1() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("A","B","C")
                .expectErrorMessage("Exception Occurred")
                .verify();


    }

    /**
     * use of stepverifier
     */
    @Test
    public void fluxTestElementsCount_WithError() {

        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();


    }

    @Test
    public void monoTest(){

        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();

    }

    /**
     * stepverifier for mono having error
     */
    @Test
    public void monoTest_Error(){


        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();

    }
}
