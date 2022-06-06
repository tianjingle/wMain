package com.scaffold.zmain.api;

import com.scaffold.zmain.application.StockServiceImpl;
import com.scaffold.zmain.db.noun.FanzhuanPo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class FanzhuanController {


    @ResponseBody
    @GetMapping(value = "/fanzhuan")
    public List<FanzhuanPo> fanzhuan(){
        return StockServiceImpl.fanzhuanCache;
    }
}
