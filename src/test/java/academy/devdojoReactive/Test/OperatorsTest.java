package academy.devdojoReactive.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class OperatorsTest {
    //Publish on******************************************Subscribeon
//    @Test
//    public void publishOnSimple(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                return i;
//                })
//                .publishOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }
//
//
//
//
//    public void subscribeOnSimple(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                    return i;
//                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }

//    @Test
//    public void multiSubscribeOnSimple(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .subscribeOn(Schedulers.single())
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                    return i;
//                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }


//    @Test
//    public void multiplepublishOnSimple(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .publishOn(Schedulers.single())
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                    return i;
//                })
//                .publishOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }


//    @Test
//    public void publishAndSubscriberSimple(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .publishOn(Schedulers.single())
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                    return i;
//                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }


//    @Test
//    public void subscribeAndPublishOn(){
//        Flux<Integer> flux = Flux.range(1,4)
//                .subscribeOn(Schedulers.single())
//                .map(i -> {
//                    log.info("Map1 - Number {} on Threat {}", 1, Thread.currentThread().getName());
//                    return i;
//                })
//                .publishOn(Schedulers.boundedElastic())
//                .map( i -> {
//                    log.info("Map 2 -  Number {} on Thread {}", i, Thread.currentThread().getName());
//                    return i;
//                });
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNext(1,2,3,4)
//                .verifyComplete();
//    }


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
}