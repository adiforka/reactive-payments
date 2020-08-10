package com.adison.reactive.reactivepayments;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

//a wacky reactive controller
@Component
@RequiredArgsConstructor
public class PaymentHandler {

    private static final Duration PAYMENT_DURATION = Duration.ofSeconds(1);

    private final PaymentGenerator paymentGenerator;

    //get one payment
    public Mono<ServerResponse> getPayment(ServerRequest serverRequest) {
        return createResponse(paymentGenerator.paymentStream(PAYMENT_DURATION).take(1), MediaType.APPLICATION_JSON);
    }

    //get a stream of payments
    public Mono<ServerResponse> getPayments(ServerRequest serverRequest) {
        return createResponse(paymentGenerator.paymentStream(PAYMENT_DURATION), MediaType.APPLICATION_STREAM_JSON);
    }


    private Mono<ServerResponse> createResponse(Flux<Payment> paymentFlux, MediaType mediaType) {
        return ServerResponse.ok()
                .contentType(mediaType)
                .body(paymentFlux, Payment.class);
    }
}
