package com.kafka.stream.rective.config;

import com.kafka.stream.rective.model.StockUpdate;
import com.kafka.stream.rective.service.StockPriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class StockStreamConfig {

    private final StockPriceService stockPriceService;

    public StockStreamConfig(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @Bean
    public Function<Flux<Message<StockUpdate>>, Flux<Message<StockUpdate>>> stockProcessor() {
        return stockPriceService::processStockUpdates;
    }
}