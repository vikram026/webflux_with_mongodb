package com.nisum.webflux.basicOperators;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

/**
 * transformation operator
 * same as java 8
 * map,flatmap,filter,flatmapMany
 */
public class FluxAndMonoTransformTest {

    List<String> names = Arrays.asList("adam", "anna", "jack", "jenny");

    /**
     * map uses
     */
    @Test
    public void transformUsingMap() {

        Flux<String> namesFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase()) //ADAM, ANNA, JACK, JENNY
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("ADAM", "ANNA", "JACK", "JENNY")
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_Length() {

        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s -> s.length()) //ADAM, ANNA, JACK, JENNY
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4,4,4,5)
                .verifyComplete();
    }

    /**
     * map with repeat operator
     */
    @Test
    public void transformUsingMap_Length_repeat() {

        Flux<Integer> namesFlux = Flux.fromIterable(names)
                .map(s -> s.length()) //ADAM, ANNA, JACK, JENNY
                .repeat(1)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext(4,4,4,5,4,4,4,5)
                .verifyComplete();

    }

    /**
     * filter and map
     */
    @Test
    public void transformUsingMap_Filter() {

        Flux<String> namesFlux = Flux.fromIterable(names)
                .filter(s -> s.length()>4)
                .map(s -> s.toUpperCase()) // JENNY
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("JENNY")
                .verifyComplete();

    }

    /**
     * flatmap impl
     * converting flux of list of data to
     * flux of data using flatmap
     * it flatten the data and then map;
     */
    @Test
    public void tranformUsingFlatMap(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) // A, B, C, D, E, F
                .flatMap(s -> {

                    return Flux.fromIterable(convertToList(s)); // A -> List[A, newValue] , B -> List[B, newValue]
                })//db or external service call that returns a flux -> s -> Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    private List<String> convertToList(String s)  {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }

    /**
     * getting parallelism on flatmap;
     */
    @Test
    public void tranformUsingFlatMap_usingparallel(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) // Flux<String>
                .window(2) //Flux<Flux<String> -> (A,B), (C,D), (E,F)
                .flatMap((s) ->
                    s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>
                    .flatMap(s -> Flux.fromIterable(s)) //Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

    /**
     * flatMapSequential
     * for using parallalism as well as maintaining the order of the data
     */
    @Test
    public void tranformUsingFlatMap_parallel_maintain_order(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F")) // Flux<String>
                .window(2) //Flux<Flux<String> -> (A,B), (C,D), (E,F)
               /* .concatMap((s) ->
                        s.map(this::convertToList).`(parallel())) */// Flux<List<String>
                .flatMapSequential((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s)) //Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(12)
                .verifyComplete();
    }

}
