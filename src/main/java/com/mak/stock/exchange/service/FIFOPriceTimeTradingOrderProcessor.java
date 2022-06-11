package com.mak.stock.exchange.service;

import com.mak.stock.exchange.model.OrderDetail;
import com.mak.stock.exchange.model.TradingDetail;
import com.mak.stock.exchange.model.TradingOption;
import com.mak.stock.exchange.service.api.TradingOrderProcessor;

import java.util.*;

public class FIFOPriceTimeTradingOrderProcessor implements TradingOrderProcessor {

    private final Map<String, PriorityQueue<OrderDetail>> buyOrders = new HashMap<>();
    private final Map<String, PriorityQueue<OrderDetail>> sellOrders = new HashMap<>();
    private final List<TradingDetail> allProcessedOrders = new ArrayList<>();

    @Override
    public List<TradingDetail> process(List<OrderDetail> orderDetails) {
        orderDetails.forEach(this::process);
        return allProcessedOrders;
    }

    private void process(OrderDetail orderDetail) {
        switch (orderDetail.getTradingOption()) {
            case BUY:
                handleBuyTrading(orderDetail);
                break;
            case SELL:
                handleSellTrading(orderDetail);
                break;
            default:
                throw new RuntimeException("Can not handle trading option :" + orderDetail.getTradingOption());
        }
    }

    private void handleSellTrading(OrderDetail sellOrder) {
        addToOrders(sellOrder);
        notifyNewSellOrderAdded(sellOrder);
    }

    private void notifyNewSellOrderAdded(OrderDetail sellOrder) {
        PriorityQueue<OrderDetail> buyOrder = buyOrders.get(sellOrder.getStockName());
        if (buyOrder != null) {
            PriorityQueue<OrderDetail> buyOrdersClone = new PriorityQueue<>(buyOrder);
            buyOrder.clear();
            while (buyOrdersClone.peek() != null) handleBuyTrading(buyOrdersClone.poll());
        }
    }

    private void handleBuyTrading(OrderDetail buyOrder) {
        if (canBuyOrderProcessed(buyOrder)) {
            PriorityQueue<OrderDetail> sellOrder = sellOrders.get(buyOrder.getStockName());
            OrderDetail order = sellOrder.poll();
            if (order.getQuantity() >= buyOrder.getQuantity()) {
                long remainingQuantity = order.getQuantity() - buyOrder.getQuantity();
                allProcessedOrders.add(new TradingDetail(buyOrder.getOrderId(), order.getPrice(), buyOrder.getQuantity(), order.getOrderId()));
                if (remainingQuantity != 0) addToOrders(order.withQuantity(remainingQuantity));
            } else {
                long remainingQuantity = buyOrder.getQuantity() - order.getQuantity();
                allProcessedOrders.add(new TradingDetail(buyOrder.getOrderId(), order.getPrice(), order.getQuantity(), order.getOrderId()));
                handleBuyTrading(buyOrder.withQuantity(remainingQuantity));
            }
        } else {
            addToOrders(buyOrder);
        }

    }

    private void addToOrders(OrderDetail orderDetail) {
        if (TradingOption.BUY == orderDetail.getTradingOption()) {
            buyOrders.computeIfAbsent(orderDetail.getStockName(), (unused) -> new PriorityQueue<>(Comparator
                    .comparing(OrderDetail::getPrice).reversed().thenComparing(OrderDetail::getOrderId))).add(orderDetail);
        }

        if (TradingOption.SELL == orderDetail.getTradingOption()) {
            sellOrders.computeIfAbsent(orderDetail.getStockName(), unused -> new PriorityQueue<>(Comparator
                    .comparing(OrderDetail::getPrice))).add(orderDetail);
        }
    }

    public boolean canBuyOrderProcessed(OrderDetail buyOrder) {
        PriorityQueue<OrderDetail> sellOrder = sellOrders.get(buyOrder.getStockName());
        return sellOrder != null && sellOrder.peek() != null && isBuyOrderPriceGtOrEqToSellOrder(buyOrder, sellOrder.peek());
    }

    private boolean isBuyOrderPriceGtOrEqToSellOrder(OrderDetail buyOrder, OrderDetail sellOrder) {
        return buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0;
    }

    public List<TradingDetail> getAllProcessedOrders() {
        return allProcessedOrders;
    }
}
