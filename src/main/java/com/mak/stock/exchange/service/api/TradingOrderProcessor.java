package com.mak.stock.exchange.service.api;

import com.mak.stock.exchange.model.OrderDetail;
import com.mak.stock.exchange.model.TradingDetail;

import java.util.List;

public interface TradingOrderProcessor {

    List<TradingDetail> process(List<OrderDetail> orderDetails);
}
