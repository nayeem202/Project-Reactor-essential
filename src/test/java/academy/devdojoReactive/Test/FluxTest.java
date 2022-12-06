package academy.devdojoReactive.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxTest {
////    @Test
////    public void fluxSubscriber(){
////        Flux<String> fluxString = Flux.just("Nayeem", "Mehedi", "Khalilullah").log();
////        StepVerifier.create(fluxString).expectNext("Nayeem", "Mehedi", "Khalilullah")
////                .verifyComplete();
////
////    }
////
//@Test
//public void fluxSubscriberNumbers(){
//    Flux<Integer> flux = Flux.range(1, 5)
//            .log();
//
//    flux.subscribe(i -> log.info("Number {}", i));
//            log.info("----------------------");
//
//    StepVerifier.create(flux).expectNext(1,2,3,4,5)
//            .verifyComplete();
//
//}


//    @Test
//    public void fluxSubscriberFromList(){
//Flux<Integer> flux = Flux.fromIterable(List.of(1,2,3,4,5))
//                .log();
//
//        flux.subscribe(i -> log.info("Number {}", i));
//        log.info("----------------------");
//
//        StepVerifier.create(flux).expectNext(1,2,3,4,5)
//                .verifyComplete();
//
//    }


//    @Test
//    public void fluxSubscriberNumbersError(){
//        Flux<Integer> fluxInteger = Flux.range(1,7)
//                .log()
//                .map(i -> {
//                    if(i ==5){
//                        throw new IndexOutOfBoundsException("Index Error!");
//                    }
//                    return i;
//                });
//        fluxInteger.subscribe(s -> log.info("Integer {}", s),
//                Throwable::printStackTrace,
//                () -> log.info("DONE!"), subscription -> subscription.request(4)
//        );
//
//        log.info("******************************");
//        StepVerifier.create(fluxInteger)
//                .expectNext(1,2,3,4)
//                .expectError(IndexOutOfBoundsException.class)
//                .verify();
//    }

    @Test
    public void fluxSubscriberNumberBackpress(){
        Flux<Integer> flux = Flux.range(1,7)
                .log();
      flux.subscribe(new Subscriber<Integer>() {
          private int count = 0;
          private Subscription subscription;
          private int requestCount = 2;

          @Override
          public void onSubscribe(Subscription subscription) {
              subscription.request(2);
          }

          @Override
          public void onNext(Integer integer) {
            count++;
            if(count >= 0 ){
                subscription.request(requestCount);
            }

          }

          @Override
          public void onError(Throwable throwable) {

          }

          @Override
          public void onComplete() {

          }
      });

//
//        log.info("******************************");
//        StepVerifier.create(flux)
//                .expectNext(1,2,3,4,5,6,7,8,9)
//                .expectError(IndexOutOfBoundsException.class)
//                .verify();
    }


}