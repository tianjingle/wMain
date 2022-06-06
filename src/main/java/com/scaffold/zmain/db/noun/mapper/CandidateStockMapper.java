package com.scaffold.zmain.db.noun.mapper;

import com.scaffold.zmain.db.noun.CandidateStockPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author tianjl
 * @Date 2021/10/27 9:54
 * @Discription disc
 */
public interface CandidateStockMapper {

    List<CandidateStockPo> findAll(@Param("currentDate")String currentDate);

    CandidateStockPo queryByCode(@Param("code") String code);

    CandidateStockPo todayDate(@Param("key") String key);

    List<String> timeLimit(@Param("limit") int limit);

    List<CandidateStockPo> findRecent(@Param("date") String date,@Param("now") String now);

    List<CandidateStockPo> findAllTemp();
}
