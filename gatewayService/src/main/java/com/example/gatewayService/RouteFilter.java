package com.example.gatewayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RouteFilter implements GlobalFilter {
    private static final Logger log = LoggerFactory.getLogger(RouteFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Get the HTTP request
        var request = exchange.getRequest();

        log.info("RouteFilter: {} request to {}",
                request.getMethod(),
                request.getURI().toString()
        );

        // Continue the filter chain
        return chain.filter(exchange);
    }
}