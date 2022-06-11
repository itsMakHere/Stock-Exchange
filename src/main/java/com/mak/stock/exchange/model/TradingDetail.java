package com.mak.stock.exchange.model;

import java.math.BigDecimal;

public class TradingDetail {

    private final String buyOrderId;
    private final BigDecimal sellPrice;
    private final long quantity;
    private final String sellId;

    public TradingDetail(String buyOrderId, BigDecimal sellPrice, long quantity, String sellId) {
        this.buyOrderId = buyOrderId;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.sellId = sellId;
    }

    public String getBuyOrderId() {
        return buyOrderId;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getSellId() {
        return sellId;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", buyOrderId, sellPrice, quantity, sellId);
    }
}
