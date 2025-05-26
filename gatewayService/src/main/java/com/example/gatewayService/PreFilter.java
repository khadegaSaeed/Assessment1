package com.example.gatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var request = exchange.getRequest();
        log.info("PreFilter: {} request to {}",
                request.getMethod(),
                request.getURI());

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;  // Same filter order as original
    }
}