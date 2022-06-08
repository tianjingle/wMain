package com.scaffold.zmain.application;

import com.scaffold.zmain.application.service.FanzhunService;
import com.scaffold.zmain.db.noun.FanzhuanPo;
import com.scaffold.zmain.db.noun.mapper.FanzhuanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FanzhuanServiceImpl implements FanzhunService {


    @Autowired
    private FanzhuanMapper fanzhuanMapper;


    @Override
    public List<FanzhuanPo> find() {
        return fanzhuanMapper.find();
    }
}
