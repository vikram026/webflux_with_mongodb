package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * some basic operators of Flux and mono
 */
public class FluxAndMonoFactoryTest {

    List<String> names = Arrays.asList("adam","anna","jack","jenny");

    /**
     * fromIterable to get flux of  collections
     */
    @Test
    public void fluxUsingIterable(){

        Flux<String> namesFlux = Flux.fromIterable(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();
    }

    /**
     * fromArray
     */
    @Test
    public void fluxUsingArray(){

        String[] names = new String[]{"adam","anna","jack","jenny"};

        Flux<String> namesFlux = Flux.fromArray(names);
        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();

    }

    /**
     * fromStream for converting into flux from any stream;
     */
    @Test
    public void fluxUsingStream(){

        Flux<String> namesFlux = Flux.fromStream(names.stream());

        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();

    }

    /**
     * justOrEmpty
     */
    @Test
    public void monoUsingJustOrEmpty(){

        Mono<String> mono = Mono.justOrEmpty(null); //Mono.Empty();

        StepVerifier.create(mono.log())
                .verifyComplete();
    }

    /**
     *  fromSupplier of mono
     *  it takes the input as supplier
     */
    @Test
    public void monoUsingSupplier(){

        Supplier<String> stringSupplier = () -> "adam";

        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);

        System.out.println(stringSupplier.get());

        StepVerifier.create(stringMono.log())
                .expectNext("adam")
                .verifyComplete();
    }

    /**
     * Creating flux using range
     * it takes the initial value and count of the input
     */
    @Test
    public void fluxUsingRange(){

       Flux<Integer> integerFlux = Flux.range(1,5).log();

       StepVerifier.create(integerFlux)
               .expectNext(1,2,3,4,5)
               .verifyComplete();
    }




}
