package academy.devdojoReactive.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;


/**
 Reactive stream
 1/Asynchronous
 2/ Non-Blocking
 3/ backpressure
 * Publisher
 * Subscription
 */
 @Slf4j
public class MonoTest {



//    @Test
//    public void monoSubscriber(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name).log();
//
//       mono.subscribe();
//       log.info("..................................");
//       StepVerifier.create(mono)
//                       .expectNext(name)
//                .verifyComplete();
//        log.info("Mono {}",mono);
//       log.info("Everything working as Intended");
//    }


//    @Test
//    public void monoSubscriberConsumer(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name).log();
//
//        mono.subscribe(s ->log.info("value {}", s)) ;
//        log.info("..................................");
//        StepVerifier.create(mono)
//                .expectNext(name)
//                .verifyComplete();
//        log.info("Mono {}",mono);
//        log.info("Everything working as Intended");
//    }








//        @Test
//    public void monoSubscriberConsumer(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name)
//                .map(s -> { throw new RuntimeException("Testing Mono with error");
//                }
//                );
//
//        mono.subscribe(s ->log.info("value {}", s), s -> log.error("Someting bad happend"));
//        log.info("..................................");
//        StepVerifier.create(mono)
//                .expectError(RuntimeException.class)
//                .verify();
//        log.info("Mono {}",mono);
//        log.info("Everything working as Intended");
//    }




//        @Test
//    public void monoSubscriberConsumerComplete(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name).log()
//                .map(String::toUpperCase);
//
//        mono.subscribe(s -> log.info("value {}", s),
//                Throwable::printStackTrace,
//                () -> log.info("Finished"));
//        log.info("..................................");
//        StepVerifier.create(mono)
//                .expectNext(name.toUpperCase())
//                .verifyComplete();
//        log.info("Mono {}",mono);
//        log.info("Everything working as Intended");
//    }
//
//    @Test
//    public void monoSubscriberConsumersubscription(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name).log()
//                .map(String::toUpperCase);
//
//        mono.subscribe(s -> log.info("value {}", s),
//                Throwable::printStackTrace,
//                () -> log.info("Finished"),
//                subscription -> subscription.request(5));
//        log.info("..................................");
//        StepVerifier.create(mono)
//                .expectNext(name.toUpperCase())
//                .verifyComplete();
//    }


//            @Test
//    public void monoDoOnMethod(){
//        String name = "Nayeem Ahmed";
//        Mono<String> mono = Mono.just(name).log()
//                        .map(String::toUpperCase).
//                doOnSubscribe(subscription -> log.info("Subscribed"))
//                .doOnRequest(lonNumber-> log.info("Request Received starting doing something"))
//                        .doOnNext(s ->log.info("Value is here . executing do Next{}", s))
//                .doOnSuccess(s -> log.info("doOnSuccess executed"));
//
//        mono.subscribe(s -> log.info("value {}", s),
//                Throwable::printStackTrace,
//                () -> log.info("Finished"));
//        log.info("..................................");
//        StepVerifier.create(mono)
//                .expectNext(name.toUpperCase())
//                .verifyComplete();
//        log.info("Mono {}",mono);
//        log.info("Everything working as Intended");
//    }

/*@Test
    public void monoDoOnError(){
    String name =  "Nayeem Ahmed";
    Mono<Object> error =     Mono.error(new IllegalArgumentException("Illegal Argument exception"))
            .doOnError(e -> log.error("Error message: {}", e.getMessage()))
            .doOnNext( s -> log.info("executing this do on next") )
            .log();


     StepVerifier.create(error).expectError(IllegalArgumentException.class)
            .verify();

    }*/

/*    @Test
        public void monoOnErrorResume(){
        String name =  "Nayeem Ahmed";
        Mono<Object> error =     Mono.error(new IllegalArgumentException("Illegal Argument exception"))
                .onErrorReturn("empty")
                .doOnError(e -> log.error("Error message: {}", e.getMessage()))
                .onErrorResume( s -> {log.info("Inside on error resume");
                return Mono.just(name);
                } )
                .doOnError((e -> MonoTest.log.error("error message {}" , e.getMessage())))
                .log();


        StepVerifier.create(error)
                .expectNext("Empty").verifyComplete();


    }*/

    @BeforeAll
    public static void setUp(){
        BlockHound.install();
    }

    @Test
    public void blockHoundWorks(){
        try {
            FutureTask<?> task =  new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });

            Schedulers.parallel().schedule(task);
            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("Should Fail");

        } catch (Exception e) {
           Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }



/*
    public void monoSubscriber(){
        String name = "Nayeem Ahmed";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe();
        log.info("-------------------");
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();


    }
*/




}



