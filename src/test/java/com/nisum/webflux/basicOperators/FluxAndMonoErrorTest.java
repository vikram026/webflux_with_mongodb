package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * handling error reactor api
 * understanding error operators
 */
public class FluxAndMonoErrorTest {
    /**
     * onErrorResume method
     * this block will execute if error event is occured .
     * it return the default/flux or mono function added by programmer;
     */
    @Test
    public void fluxErrorHandling() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorResume((e) -> {    // this block gets executed
                    System.out.println("Exception is : " + e);
                    return Flux.just("default", "default1");
                });

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                //.expectError(RuntimeException.class)
                //.verify();
                .expectNext("default", "default1")
                .verifyComplete();

    }

    /**
     * here it take value as input and returns a flux;
     * as it internally uses the onErrorResume method
     */
    @Test
    public void fluxErrorHandling_OnErrorReturn() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default");

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("default")
                .verifyComplete();
    }

    /**
     * we can call the onErrorMap
     * if we are getting exception and
     * there we want to call any custom exception class
     */
    @Test
    public void fluxErrorHandling_OnErrorMap() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();

    }

    /**
     * retry() method
     * giving the user a chance to try 2 more time
     */
    @Test
    public void fluxErrorHandling_OnErrorMap_withRetry() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e))
                .retry(2);

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(CustomException.class)
                .verify();

    }

    /**
     * use of retryBackoff
     * adding timestamp in retry
     */
    @Test
    public void fluxErrorHandling_OnErrorMap_withRetryBackoff() {

        Flux<String> stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("D"))
                .onErrorMap((e) -> new CustomException(e))
                .retryBackoff(2, Duration.ofSeconds(5));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectNext("A", "B", "C")
                .expectError(IllegalStateException.class)
                .verify();

    }


}
