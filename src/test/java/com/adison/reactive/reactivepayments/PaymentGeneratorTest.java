package com.adison.reactive.reactivepayments;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class PaymentGeneratorTest {

    private final PaymentGenerator paymentGenerator = new PaymentGenerator();

    @Test
    void shouldGeneratePayments() throws InterruptedException {
       /* //use a latch to block the test until stream completes
        CountDownLatch countDownLatch = new CountDownLatch(1);
        paymentGenerator.paymentStream(Duration.ofSeconds(1))
                *//*.filter(payment -> payment.getAmount() > 10)
                .map(Payment::getTransactionId)*//*
                .subscribe(this::onPayment, this::onError, countDownLatch::countDown);
        countDownLatch.await();*/

        //the ultimate badass stream verifier
        //take is like the subscriber's limit
        StepVerifier.create(paymentGenerator.paymentStream(Duration.ofSeconds(1)).take(2))
                .recordWith(ArrayList::new)
                .consumeNextWith(Payment::hasTransactionId)
                .expectNextCount(1)
                .consumeRecordedWith(payments -> assertThat(payments).allMatch(Payment::hasTransactionId))
                .verifyComplete();

    }

    private void onPayment(Payment payment) {
        System.out.println(payment);
    }

    public void onError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
    }

}
