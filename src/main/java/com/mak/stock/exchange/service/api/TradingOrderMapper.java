package com.mak.stock.exchange.service.api;

import com.mak.stock.exchange.model.OrderDetail;

import java.util.List;

public interface TradingOrderMapper {
    List<OrderDetail> map(String value) throws Exception;
}
