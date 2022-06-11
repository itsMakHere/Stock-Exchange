package com.mak.stock.exchange.model;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

public class OrderDetail {

    private final String orderId;
    private final LocalTime time;
    private final String stockName;
    private final TradingOption tradingOption;
    private final BigDecimal price;
    private final long quantity;

    public OrderDetail(String orderId, LocalTime time, String stockName, TradingOption tradingOption, BigDecimal price, long quantity) {
        this.orderId = Objects.requireNonNull(orderId);
        this.time = Objects.requireNonNull(time);
        this.stockName = Objects.requireNonNull(stockName);
        this.tradingOption = Objects.requireNonNull(tradingOption);
        this.price = Objects.requireNonNull(price);
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStockName() {
        return stockName;
    }

    public TradingOption getTradingOption() {
        return tradingOption;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public OrderDetail withQuantity(long quantity) {
        return new OrderDetail(orderId, time, stockName, tradingOption, price, quantity);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", time=" + time +
                ", stockName='" + stockName + '\'' +
                ", tradingOption=" + tradingOption +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
