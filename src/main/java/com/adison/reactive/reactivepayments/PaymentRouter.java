package com.adison.reactive.reactivepayments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

//maps requests to controller (handler) methods
@Configuration
public class PaymentRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(PaymentHandler paymentHandler) {
        return RouterFunctions
                .route(GET("/payment").and(accept(MediaType.APPLICATION_JSON)), paymentHandler::getPayment)
                .andRoute(GET("/payments").and(accept(MediaType.APPLICATION_STREAM_JSON)), paymentHandler::getPayments);

    }
}
