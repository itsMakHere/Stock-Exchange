package com.mak.stock.exchange;

import com.mak.stock.exchange.model.TradingDetail;
import com.mak.stock.exchange.service.FIFOPriceTimeTradingOrderProcessor;
import com.mak.stock.exchange.service.FileTradingOrderMapper;
import com.mak.stock.exchange.service.TradingOrderService;
import com.mak.stock.exchange.service.api.TradingOrderMapper;

import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) throw new RuntimeException("Please provide the input test file");
        TradingOrderMapper tradingOrderMapper = new FileTradingOrderMapper();
        FIFOPriceTimeTradingOrderProcessor tradingOrderProcessor = new FIFOPriceTimeTradingOrderProcessor();
        TradingOrderService tradingOrderService = new TradingOrderService(tradingOrderMapper, tradingOrderProcessor);
        List<TradingDetail> tradingDetails = tradingOrderService.process(args[0]);
        tradingDetails.forEach(System.out::println);
    }


}
