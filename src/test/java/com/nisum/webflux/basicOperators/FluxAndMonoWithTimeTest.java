package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Playing with flux with timestamp and delays;
 */
public class FluxAndMonoWithTimeTest {
    /**
     * uses
     * to create infinite sequence of the flux;
     * @throws InterruptedException
     */
    @Test
    public void infiniteSequence() throws InterruptedException {

        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(100))
                .log(); // starts from 0 --> ......

        infiniteFlux
                .subscribe((element) -> System.out.println("Value is : " + element));

        Thread.sleep(3000);

    }

    /**
     * use of flux with take method
     * its like limit of the flux
     * @throws InterruptedException
     */


    @Test
    public void infiniteSequenceTest() throws InterruptedException {

        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0L, 1L,2L)
                .verifyComplete();

    }

    /**
     * creating infinite sequence of flux and use of map and take operator
     * @throws InterruptedException
     */
    @Test
    public void infiniteSequenceMap() throws InterruptedException {

        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1,2)
                .verifyComplete();

    }

    /**
     * creating the flux with delay
     *
     * @throws InterruptedException
     */
    @Test
    public void infiniteSequenceMap_withDelay() throws InterruptedException {

        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .delayElements(Duration.ofSeconds(1))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0, 1,2)
                .verifyComplete();

    }

}
