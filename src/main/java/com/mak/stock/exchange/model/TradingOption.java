package com.mak.stock.exchange.model;

public enum TradingOption {
    BUY("buy"), SELL("sell");


    private final String value;

    TradingOption(String value) {
        this.value = value;
    }

    public static TradingOption fromString(String option){
        return TradingOption.valueOf(option.toUpperCase());
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
