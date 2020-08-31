package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

/**
 * Use of Virtual Timing Mostly is used in writing testcases;
 * to save time to wait;
 */
public class VirtualTimeTest {
    /**
     * Writing testcase WithoutVirtualTime
     * when we run it takes time to wait for the actual amount of time
     */

    @Test
    public void testingWithoutVirtualTime(){

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);

        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0l,1l,2l)
                .verifyComplete();
    }

    /**
     * once we use
     */
    @Test
    public void testingWithVirtualTime(){

        VirtualTimeScheduler.getOrSet();

        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3);

        StepVerifier.withVirtualTime(() -> longFlux.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0l,1l,2l)
                .verifyComplete();

    }
}
