package academy.devdojoReactive.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class OperatorsTest {
    //Publish on******************************************Subscribeon
    @Test
    public void publishOnSimple(){
        Flux<Integer> flux = Flux.range(1,4)
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }




    public void subscribeOnSimple(){
        Flux<Integer> flux = Flux.range(1,4)
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }

    @Test
    public void multiSubscribeOnSimple(){
        Flux<Integer> flux = Flux.range(1,4)
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }


    @Test
    public void multiplepublishOnSimple(){
        Flux<Integer> flux = Flux.range(1,4)
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }


    @Test
    public void publishAndSubscriberSimple(){
        Flux<Integer> flux = Flux.range(1,4)
                .publishOn(Schedulers.single())
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }


    @Test
    public void subscribeAndPublishOn(){
        Flux<Integer> flux = Flux.range(1,4)
                .subscribeOn(Schedulers.single())
                .map(i -> {
                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
                    return i;
                })
                .publishOn(Schedulers.boundedElastic())
                .map( i -> {
                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
                    return i;
                });

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(1,2,3,4)
                .verifyComplete();
    }


    @Test
    public void subscribeOnIO() throws Exception {
        Mono<List<String>> list = Mono.fromCallable(() -> Files.readAllLines(Path.of("text-file")))
                .log()
                .subscribeOn(Schedulers.boundedElastic());

        StepVerifier.create(list)
                .expectSubscription()
                .thenConsumeWhile(l -> {
                    Assertions.assertFalse(l.isEmpty());
                    log.info("Size {}", l.size());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void switchEmptyOperator() {
        Flux<Object> flux = emptyFlux()
                .switchIfEmpty(Flux.just("Not empty Anymore"))
                .log();

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(flux)
                .verifyComplete();
    }


    @Test
    public void deferOperator() throws Exception {
        Mono<Long> just = Mono.just(System.currentTimeMillis());
        Mono<Long> defer = Mono.defer(() -> Mono.just(System.currentTimeMillis()));

        defer.subscribe(l -> log.info ("time {}", l));
        Thread.sleep(100);
        defer.subscribe(l -> log.info ("time {}", l));
        Thread.sleep(100);
        defer.subscribe(l -> log.info ("time {}", l));
        Thread.sleep(100);
        defer.subscribe(l -> log.info ("time {}", l));
        Thread.sleep(100);

        AtomicLong atomlong = new AtomicLong();
        defer.subscribe(atomlong:: set);
        Assertions.assertTrue(atomlong.get() > 0);

    }
    private Flux<Object> emptyFlux() {
        return Flux.empty();
    }


    //Concat with, combineLatest
/*
    @Test
    public void concatOperator(){
       Flux<String> flux1 = Flux.just("a", "b");
       Flux<String> flux2 =  Flux.just("c", "d");

           Flux<String> concatFlux =  Flux.concat(flux1,flux2).log();
        StepVerifier.create(concatFlux)
                .expectSubscription()
                .expectNext("a", "b", "c", "d")
                .expectComplete()
                .verify();

    }
*/


    @Test
    public void concatOperator(){
        Flux<String> flux1 = Flux.just("a", "b");
        Flux<String> flux2 =  Flux.just("c", "d");

        Flux<String> concatFlux =  Flux.concat(flux1,flux2).log();
        StepVerifier.create(concatFlux)
                .expectSubscription()
                .expectNext("a", "b", "c", "d")
                .expectComplete()
                .verify();

    }


    @Test
    public void concatWithOperator(){
        Flux<String> flux1 = Flux.just("a", "b");
        Flux<String> flux2 =  Flux.just("c", "d");

        Flux<String> concatFlux =  flux1.concatWith(flux2)
                .log();
        StepVerifier.create(concatFlux)
                .expectSubscription()
                .expectNext("a", "b", "c", "d")
                .expectComplete()
                .verify();

    }

    //***************Combine latest Operator
        @Test
    public void combineLatestOperator(){
        Flux<String> flux1 = Flux.just("a", "b");
        Flux<String> flux2 =  Flux.just("c", "d");

        Flux<String> combinelatest = Flux.combineLatest(flux1,flux2,
                        (s1,s2) -> s1.toUpperCase()+ s2.toUpperCase())
                .log();

        StepVerifier.create(combinelatest)
                .expectSubscription()
                .expectNext("BC", "BD")
                .expectComplete()
                .verify();

    }


    //************************MergeFlux*****************************

    @Test
    public void mergeOperator() throws InterruptedException {
        Flux<String> flux1 = Flux.just("a", "b").delayElements(Duration.ofMillis(200));
        Flux<String> flux2 = Flux.just("c" , "d");
        Flux <String> mergeFlux= Flux.merge(flux1,flux2)
                .delayElements(Duration.ofMillis(200))
                .log();
        mergeFlux.subscribe(log::info);
        Thread.sleep(1000);



        mergeFlux.subscribe(log::info);
        StepVerifier.create(mergeFlux)
                .expectSubscription()
                .expectNext("a" , "b", "c", "d")
                .expectComplete()
                .verify();

    }


    //*****************MergeWith*********************888
    @Test
    public void mergeWithOperator() throws InterruptedException {
        Flux<String> flux1 = Flux.just("a", "b").delayElements(Duration.ofMillis(200));
        Flux<String> flux2 = Flux.just("c" , "d");
        Flux <String> mergeFlux= flux1.mergeWith(flux2)
                .delayElements(Duration.ofMillis(200))
                .log();
        mergeFlux.subscribe(log::info);
        Thread.sleep(1000);

        mergeFlux.subscribe(log::info);
        StepVerifier.create(mergeFlux)
                .expectSubscription()
                .expectNext("c" , "d", "a", "b")
                .expectComplete()
                .verify();

    }

    //*******************************8ConcatDelayError, Merge Delay Error*****************************8

        @Test
    public void mergeSequentialOperator() throws InterruptedException {
        Flux<String> flux1 = Flux.just("a", "b").delayElements(Duration.ofMillis(200));
        Flux<String> flux2 = Flux.just("c" , "d");

        Flux <String> mergeFlux= Flux.mergeSequential(flux1,flux2, flux1)
                .delayElements(Duration.ofMillis(200))
                .log();


        mergeFlux.subscribe(log::info);
        StepVerifier.create(mergeFlux)
                .expectSubscription()
                .expectNext("a" , "b", "c", "d", "a" , "b")
                .expectComplete()
                .verify();

    }


    //**********************ConcatOperatorWithError*************************
    @Test
    public void concatOperatorError() {
        Flux<String> flux1 = Flux.just("a", "b")
                .map(s -> {
                    if (s.equals("b")) {
                        throw new IllegalArgumentException();
                    }
                    return s;
                });


        Flux<String> flux2 = Flux.just("c", "d");

        Flux<String> concatFlux = Flux.concatDelayError(flux1, flux2).log();
        StepVerifier.create(concatFlux)
                .expectSubscription()
                .expectNext("a","c","d")
                .expectError()
                .verify();

    }


    //****************Merge delay error Operator *********************
    @Test
    public void mergedelayErrorOperator() throws InterruptedException {
        Flux<String> flux1 = Flux.just("a", "b")
                .map(s -> {
                    if (s.equals("b")) {
                        throw new IllegalArgumentException();
                    }
                    return s;
                }).doOnError(t -> log.error("we could do something with this"));

        Flux<String> flux2 = Flux.just("c", "d");


        Flux<String> mergeFlux = Flux.mergeDelayError(1, flux1, flux2, flux1)
                .log();


        mergeFlux.subscribe(log::info);
        StepVerifier.create(mergeFlux)
                .expectSubscription()
                .expectNext("a", "c", "d", "a")
                .expectError()
                .verify();

    }



    //****************************Flatmap**********************************
    @Test
    public void flatMapOperator() throws Exception {
        Flux<String> flux = Flux.just("a", "b");
        Flux<String> flatFlux = flux.map(String::toUpperCase)
                .flatMap((this::findByName))
                .log();

        StepVerifier
                .create(flatFlux)
                .expectSubscription()
                .expectNext("nameB1","nameB2", "nameA1","nameA2")
                .verifyComplete();

        flatFlux.subscribe(e -> log.info(e));
    }


//    public Flux<String> findByName(String name) {
//        return name.equals("A") ? Flux.just("nameA1", "nameA2").delayElements(Duration.ofMillis(100)) : Flux.just("nameB1", "nameB2");
//    }





    //****************************flatMapSequential**********************************
        @Test
    public void flatMapSequentialOperator() throws Exception {
        Flux<String> flux = Flux.just("a", "b");
        Flux<String> flatFlux = flux.map(String::toUpperCase)
                .flatMapSequential((this::findByName))
                .log();

        StepVerifier
                .create(flatFlux)
                .expectSubscription()
                .expectNext("nameA1","nameA2" , "nameB1","nameB2" )
                .verifyComplete();

        flatFlux.subscribe(e -> log.info(e));
    }


    public Flux<String> findByName(String name) {
        return name.equals("A") ? Flux.just("nameA1", "nameA2").delayElements(Duration.ofMillis(100)) : Flux.just("nameB1", "nameB2");
    }



}