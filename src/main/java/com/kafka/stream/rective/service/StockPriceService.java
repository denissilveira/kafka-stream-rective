package com.kafka.stream.rective.service;

import com.kafka.stream.rective.model.StockUpdate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class StockPriceService {

    private static final String TARGET_CURRENCY = "EUR";
    private static final String FILTER_HEADER_KEY = "filter-key";
    private static final String EXPECTED_HEADER_VALUE = "process";

    public Flux<Message<StockUpdate>> processStockUpdates(Flux<Message<StockUpdate>> stockFlux) {
        return stockFlux
                .filter(this::hasValidHeader)
                .flatMap(this::processStockUpdate);
    }

    private boolean hasValidHeader(Message<StockUpdate> message) {
        Object headerValueObj = message.getHeaders().get(FILTER_HEADER_KEY);
        if (headerValueObj instanceof String headerValue) {
            return EXPECTED_HEADER_VALUE.equals(headerValue);
        }
        return false;
    }

    private Mono<Message<StockUpdate>> processStockUpdate(Message<StockUpdate> message) {
        StockUpdate stockUpdate = message.getPayload();

        return convertPrice(stockUpdate)
                .map(updatedStock -> MessageBuilder.withPayload(updatedStock)
                        .copyHeaders(message.getHeaders())
                        .build());
    }

    private Mono<StockUpdate> convertPrice(StockUpdate stock) {
        double newPrice = stock.price() * 1.1;
        return Mono.just(new StockUpdate(stock.symbol(), newPrice, TARGET_CURRENCY, stock.timestamp()));
    }
}