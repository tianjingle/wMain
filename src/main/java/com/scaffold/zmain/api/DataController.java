package com.scaffold.zmain.api;

import com.scaffold.zmain.application.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tianjl
 * @Date 2022/1/14 19:33
 * @Discription disc
 */
@RestController
public class DataController {

    @Autowired
    private StockService stockService;


    @GetMapping(value = "/fetch")
    public Object getResult(int type,int pageIndex,int pageSize){
            return stockService.fetch(type,pageIndex, pageSize);
    }
}
