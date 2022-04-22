package com.scaffold.zmain.application.service;

import com.scaffold.zmain.api.vo.ShowTimeVo;
import com.scaffold.zmain.api.vo.TongJiVo;
import com.scaffold.zmain.db.noun.CandidateStockPo;

import java.io.IOException;
import java.util.List;

/**
 * @Author tianjl
 * @Date 2021/10/27 14:16
 * @Discription disc
 */
public interface StockService {
    List<CandidateStockPo> stockToday(String day, List<ShowTimeVo> showTime) throws IOException;

    CandidateStockPo queryByCode(String code);

    List<ShowTimeVo> queryShowTime();

    List<TongJiVo> tongji(String key);

    Object fetch(int type, int pageIndex, int pageSize);
}
