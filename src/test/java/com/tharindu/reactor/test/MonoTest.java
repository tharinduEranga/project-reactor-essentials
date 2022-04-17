package com.tharindu.reactor.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/*
 * Reactive Streams
 * 1. Asynchronous
 * 2. Non-blocking
 * 3. Backpressure
 * Publisher <- (subscribe) Subscriber
 * Subscription is created
 * Publisher (onSubscribe with the subscription) -> Subscriber
 * Subscription <- (request N)
 * Publisher -> (onNext) Subscriber
 * until:
 * 1. Publisher sends all the objects requested.
 * 2. Publisher sends all the objects it has (onComplete).
 * 3. If there is an error. (onError) -> subscriber and subscription will be cancelled.
 * */
@Slf4j
class MonoTest {
/*A Mono<T> is a specialized Publisher<T> that emits at most one item
and then (optionally) terminates with an onComplete signal or an onError signal.*/

    @Test
    void monoSubscriber() {
        /*Logging a mono's content using inbuilt log*/
        String name = "Tharindu Eranga";
        Mono<String> mono = Mono.just(name)
                .log();
        mono.subscribe();

        log.info("----------------------");

        /*Logging a mono's content using test*/
        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    void monoSubscriberConsumer() {
        String name = "Tharindu Eranga";
        Mono<String> mono = Mono.just(name)
                .log();
        mono.subscribe(val -> log.info("Value: {}", val));

        log.info("----------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();
    }

    @Test
    void monoSubscriberConsumerError() {
        String name = "Tharindu Eranga";
        Mono<String> mono = Mono.just(name)
                .map(val -> {
                    throw new RuntimeException("Testing mono with error");
                });
        mono.subscribe(val -> log.info("Value: {}", val), error -> {
            log.error("Something went wrong!");
            error.printStackTrace();
        });

        log.info("----------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void monoSubscriberConsumerComplete() {
        String name = "Tharindu Eranga";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);
        mono.subscribe(val -> log.info("Value: {}", val), error -> {
            log.error("Something went wrong!");
            error.printStackTrace();
        }, () -> log.info("Finished!")); //finished run after the element publishing is completed/error

        log.info("----------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    void monoSubscriberConsumerCompleteSubscription() {
        String name = "Tharindu Eranga";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);
        mono.subscribe(val -> log.info("Value: {}", val), error -> {
                    log.error("Something went wrong!");
                    error.printStackTrace();
                }, () -> log.info("Finished!"),
                Subscription::cancel); //cancelling will stop the source producing any values

        log.info("----------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();
    }

    @Test
    void monoDoOnMethods() {
        String name = "Tharindu Eranga";
        Mono<Object> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Do on subscribe: {}", subscription))
                .doOnRequest(longVal -> log.info("Request received, starting to do something."))
                .doOnNext(val -> log.info("Value is here 1: {}", val)) //executes next if there is a value to emit
                .flatMap(val -> Mono.empty()) //sets the value in the flow as empty
                .doOnNext(val -> log.info("Value is here 2: {}", val)) //won't execute
                .doOnSuccess(subscription -> log.info("Success!")); //executes even there is a value or not to emit

        mono.subscribe(val -> log.info("Subscribed: {}", val), //only runs if there's a value to emit
                Throwable::printStackTrace,
                () -> log.info("Finished!")); //finished run after the element publishing is completed/error

        log.info("----------------------");

        StepVerifier.create(mono)
                .expectComplete()
                .verify(); //A Mono<T> terminates after an onComplete signal or an onError signal.
    }
}
