package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

/**
 * combining two or more flux/mono;
 */
public class FluxAndMonoCombineTest {
    /**
     * Combine two or more flux using merge method;
     * showing result as a,b,c,d,e,f
     */
    @Test
    public void combineUsingMerge(){

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    /**
     * adding delay to she the exact behaviour of the merge method
     * showing result as a,d,b,e,f,c
     */
    @Test
    public void combineUsingMerge_withDelay(){

        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));;

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    /**
     * combine using concat method of the flux
     *
     */
    @Test
    public void combineUsingConcat(){

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.concat(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }
    /**
     * combine using concat method of the flux
     * after adding delay also it will concat first and the second flux;
     * will show the result as A,B,C,D,E,F
     */
    @Test
    public void combineUsingConcat_withDelay(){

        VirtualTimeScheduler.getOrSet();

        Flux<String> flux1 = Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(flux1,flux2);

        StepVerifier.withVirtualTime(()->mergedFlux.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(6))
                .expectNextCount(6)
                .verifyComplete();


      /*  StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();*/

    }

    /**
     * combine using Zip method
     * we can add custom function in zip method agrument
     * here we have more control on the flux;
     */
    @Test
    public void combineUsingZip(){

        Flux<String> flux1 = Flux.just("A","B","C");
        Flux<String> flux2 = Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.zip(flux1,flux2, (t1,t2) -> {
            return t1.concat(t2); // AD, BE, CF
        }); //A,D : B,E : C:F

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("AD", "BE", "CF")
                .verifyComplete();

    }


}
