package com.example.gatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ErrorFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(throwable -> {
                    ServerHttpResponse response = exchange.getResponse();
                    HttpStatusCode statusCode = response.getStatusCode() != null ?
                            response.getStatusCode() :
                            HttpStatus.INTERNAL_SERVER_ERROR;

                    log.error("ErrorFilter: Error occurred with status {} - {}",
                            statusCode.value(),
                            throwable.getMessage());

                    // You can customize the error response here if needed
                    response.setStatusCode(statusCode);
                    return Mono.empty();
                });
    }

    @Override
    public int getOrder() {
        return -1; // High precedence to catch errors early
    }
}