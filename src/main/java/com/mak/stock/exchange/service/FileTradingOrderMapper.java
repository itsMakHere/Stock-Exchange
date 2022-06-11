package com.mak.stock.exchange.service;



import com.mak.stock.exchange.model.OrderDetail;
import com.mak.stock.exchange.model.TradingOption;
import com.mak.stock.exchange.service.api.TradingOrderMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class FileTradingOrderMapper implements TradingOrderMapper {

    public List<OrderDetail> map(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath))
                .stream()
                .map(line -> line.split(" "))
                .map(splitLines -> new OrderDetail(splitLines[0], LocalTime.parse(splitLines[1]), splitLines[2], TradingOption.fromString(splitLines[3]),
                        new BigDecimal(splitLines[4]), Long.parseLong(splitLines[5])))
                .collect(Collectors.toList());


    }

}
