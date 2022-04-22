package com.scaffold.zmain.api;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author tianjl
 * @Date 2022/1/14 19:46
 * @Discription disc
 */
@Ignore
@SpringBootTest
public class DataControllerTest {

    @Resource
    private DataController dataController;

    @Test
    public void getResult() {
        System.out.println(dataController.getResult(0,1,1));
    }
}