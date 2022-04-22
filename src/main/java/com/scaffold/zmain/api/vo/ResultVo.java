package com.scaffold.zmain.api.vo;

import com.scaffold.zmain.db.noun.CandidateStockPo;

import java.util.List;

/**
 * @Author tianjl
 * @Date 2022/1/14 19:44
 * @Discription disc
 */
public class ResultVo {

    private int total;

    private int pageIndex;

    private int pageSize;

    List<CandidateStockPo> result;
}
