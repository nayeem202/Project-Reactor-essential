package academy.devdojoReactive.Test;

import lombok.extern.slf4j.Slf4j;

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

//    @Test
//    public void fluxSubscriberNumberBackpress(){
//        Flux<Integer> flux = Flux.range(1,7)
//                .log();
//      flux.subscribe(new Subscriber<Integer>() {
//          private int count = 0;
//          private Subscription subscription;
//          private int requestCount = 2;
//
//          @Override
//          public void onSubscribe(Subscription subscription) {
//              subscription.request(2);
//          }
//
//          @Override
//          public void onNext(Integer integer) {
//            count++;
//            if(count >= 0 ){
//                subscription.request(requestCount);
//            }
//
//          }
//
//          @Override
//          public void onError(Throwable throwable) {
//
//          }
//
//          @Override
//          public void onComplete() {
//
//          }
//      });

//
//        log.info("******************************");
//        StepVerifier.create(flux)
//                .expectNext(1,2,3,4,5,6,7,8,9)
//                .expectError(IndexOutOfBoundsException.class)
//                .verify();



//    @Test
//    public void fluxSubscriberNumberNotSoUglyBackpressure() {
//       Flux<Integer> flux = Flux.range(1,10)
//                       .log();
//
//        flux.subscribe(new BaseSubscriber<Integer>() {
//            private int count = 0;
//            private final int requestCount = 2;
//            @Override
//            protected void hookOnSubscribe(Subscription subscription) {
//                request(requestCount);
//            }
//
//            @Override
//            protected void hookOnNext(Integer value) {
//                count++;
//              if (count >= requestCount){
//                  count = 0;
//                 request(requestCount);
//             }
//            }
//        });
 //   }



//    @Test
//    public void fluxSubscriberIntervalOne() throws InterruptedException {
//        Flux<Long> interval = Flux.interval(Duration.ofMillis(100))
//                .take(10)
//                .log();
//        interval.subscribe(i -> log.info("Number {}", i));
//        Thread.sleep((3000));
//    }


//    @Test
//    public void fluxSubscriberIntervalTwo() throws Exception {
//        StepVerifier.withVirtualTime(this::CreateInterval)
//                .expectSubscription()
//                .thenAwait(Duration.ofDays(1))
//                .expectNext(0L)
//                .thenAwait(Duration.ofDays(1))
//                .expectNext(1L)
//                .thenCancel()
//                .verify();
//
//
//        //Flux<Long> interval = CreateInterval();
//
//    }
//
//    private  Flux<Long> CreateInterval() {
//        return Flux.interval(Duration.ofDays(1))
//                .take(10)
//                .log();
//    }




//    @Test
//    public void fluxsubscriberPrettybackpressure(){
//        Flux<Integer> flux = Flux.range(1,10)
//                .limitRate(3)
//                .log();
//
//        flux.subscribe(i -> log.info("Number {}", i));
//        log.info("-------------------");
//
//        StepVerifier.create(flux)
//
//                .expectNext(1,2,3,4,5,6).verifyComplete();
//    }



//@Test
//    public void connectableFlux() throws InterruptedException {
//    ConnectableFlux<Integer> connectableFlux = Flux.range(1,10)
//            .log()
//            .delayElements(Duration.ofMillis(100))
//            .publish();
//    connectableFlux.connect();
//    log.info("Thread sleeping for 300s");
//    Thread.sleep(300);
//    connectableFlux.subscribe(i -> log.info("sub1 number {}", i));
//    log.info("Thread sleeping for 200s");
//    Thread.sleep(200);
//    connectableFlux.subscribe( i-> log.info("sub2 number {}", i));
//    StepVerifier.create(connectableFlux)
//            .then(connectableFlux::connect)
//            .thenConsumeWhile(i -> i<=5)
//            .expectNext(6,7,8,9,10)
//            .expectComplete()
//            .verify();
//
//
//
//}


//    @Test
//    public void connectableFluxAutoConnect() throws Exception {
//    Flux<Integer> fluxAutoConnect = Flux.range(1,5)
//            .log()
//            .delayElements(Duration.ofMillis(100))
//            .publish().autoConnect(2);
//
//    StepVerifier.create(fluxAutoConnect)
//            .then(fluxAutoConnect::subscribe)
//            .expectNext(1,2,3,4,5)
//            .expectComplete()
//            .verify();
//
//
//
//}







}