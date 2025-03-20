package com.kafka.stream.rective.model;

public record StockUpdate(String symbol, double price, String currency, long timestamp) { }