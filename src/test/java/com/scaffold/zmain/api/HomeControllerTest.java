package com.scaffold.zmain.api;

import com.scaffold.zmain.api.vo.ShowTimeVo;
import com.scaffold.zmain.application.service.StockService;
import com.scaffold.zmain.db.noun.CandidateStockPo;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @Author tianjl
 * @Date 2022/1/10 22:12
 * @Discription disc
 */
@Ignore
public class HomeControllerTest {

    @Test
    public void index() {
        HomeController homeController =mock(HomeController.class);
        when(homeController.index()).thenReturn(new ModelAndView("index")).thenReturn(new ModelAndView("zhang"));
        ModelAndView modelAndView = homeController.index();
        System.out.println(modelAndView.getViewName());
        assertSame(modelAndView.getViewName(),"index");
        modelAndView = homeController.index();
        System.out.println(modelAndView.getViewName());
        Mockito.verify(homeController,times(2)).index();
    }

    @Test
    public void dapan() throws IOException {
        ModelAndView modelAndView=null;
        HomeController homeController =  spy(HomeController.class);
        StockService stockService = mock(StockService.class);
        homeController.setStockService(stockService);
        List<ShowTimeVo> result = new ArrayList<>();
        List<CandidateStockPo> candidateStockPos=new ArrayList<>();
        result.add(new ShowTimeVo("2022-01-10",1));
        result.add(new ShowTimeVo("2022-01-09",1));
        result.add(new ShowTimeVo("2022-01-08",1));
        CandidateStockPo candidateStockPo = new CandidateStockPo();
        candidateStockPo.setWenyuRiver(0);
        candidateStockPo.setShowCode("sh.00001");
        candidateStockPo.setIndustry("1");
        candidateStockPo.setCv(1);
        candidateStockPo.setZsm(1);
        candidateStockPos.add(candidateStockPo);
        when(stockService.queryShowTime()).thenReturn(result);
        when(stockService.stockToday(anyString(),anyList())).thenReturn(candidateStockPos);
        modelAndView=homeController.today("1","");
        modelAndView=homeController.today("1","");
        System.out.println(modelAndView.getViewName());
        assertSame("today",modelAndView.getViewName());
        //at least
        Mockito.verify(stockService,atLeast(1)).queryShowTime();
        //at most
        Mockito.verify(stockService,atMost(1)).queryShowTime();
        //equals
        Mockito.verify(stockService,times(1)).queryShowTime();
    }

    @Test
    public void today() {
        HomeController homeController = spy(HomeController.class);
        StockService stockService = mock(StockService.class);
        homeController.setStockService(stockService);
        when(stockService.tongji(anyString())).thenThrow(new Exception("123"));
        try {
            homeController.today("1","1");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void detail() {
    }

    @Test
    public void about() {
        //create empty object
        List tianjingle = mock(List.class);

        //
        tianjingle.add("one");
        tianjingle.add("two");

        InOrder inOrder = inOrder(tianjingle);


        inOrder.verify(tianjingle).add("one");
        inOrder.verify(tianjingle).add("two");

        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        firstMock.add("one");
        secondMock.add("1");

        InOrder inOrder1 = inOrder(firstMock, secondMock);

        inOrder1.verify(firstMock).add("one");
        inOrder1.verify(secondMock).add("1");
    }

    @Test
    public void tongji() {
    }
}