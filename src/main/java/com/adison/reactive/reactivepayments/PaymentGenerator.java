package com.adison.reactive.reactivepayments;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentGenerator {

    private static final int MAX_PAYMENT_AMOUNT = 10_000;
    private final Random random = new Random();

    public Flux<Payment> paymentStream(Duration period) {
        //zipWith waits for a pair from both streams to emit an event
        //zipWith returns tuples of payments and duration values, so we map
        //those tuples to just the payment values on them (T1)
        return Flux.generate(this::paymentSink)
                .zipWith(Flux.interval(period))
                .map(Tuple2::getT1);
    }

    private void paymentSink(SynchronousSink<Payment> sink) {
        sink.next(createPayment());
    }

    private Payment createPayment() {
        Instant timestamp = Instant.now();
        String transactionId = UUID.randomUUID().toString();
        long amount = random.nextInt(MAX_PAYMENT_AMOUNT) + 1;
        return new Payment(transactionId, amount, timestamp);
    }

}
