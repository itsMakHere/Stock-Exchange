package com.mak.stock.exchange.service;

import com.mak.stock.exchange.model.OrderDetail;
import com.mak.stock.exchange.model.TradingDetail;
import com.mak.stock.exchange.service.api.TradingOrderMapper;
import com.mak.stock.exchange.service.api.TradingOrderProcessor;

import java.util.List;

public class TradingOrderService {

    private final TradingOrderMapper tradingOrderMapper;
    private final TradingOrderProcessor tradingOrderProcessor;

    public TradingOrderService(TradingOrderMapper tradingOrderMapper, TradingOrderProcessor tradingOrderProcessor) {
        this.tradingOrderMapper = tradingOrderMapper;
        this.tradingOrderProcessor = tradingOrderProcessor;
    }

    public List<TradingDetail> process(String testFilePath) throws Exception {
        List<OrderDetail> orders = tradingOrderMapper.map(testFilePath);
        return tradingOrderProcessor.process(orders);
    }
}
