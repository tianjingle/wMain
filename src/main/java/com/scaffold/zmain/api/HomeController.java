package com.scaffold.zmain.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scaffold.zmain.api.vo.IndustryStastiscVo;
import com.scaffold.zmain.api.vo.ShowTimeVo;
import com.scaffold.zmain.api.vo.TongJiVo;
import com.scaffold.zmain.application.service.StockService;
import com.scaffold.zmain.config.MyConfig;
import com.scaffold.zmain.db.noun.CandidateStockPo;
import com.scaffold.zmain.utils.DateUtil;
import com.scaffold.zmain.utils.ImageByPython;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author tianjl
 * @Date 2021/10/27 9:45
 * @Discription disc
 */
@Controller
public class HomeController {


    @Autowired
    private StockService stockService;

    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Autowired
    private MyConfig myConfig;



    @GetMapping(value = "/")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView("index");
        return modelAndView;
    }

    @GetMapping(value = "/dapan")
    public ModelAndView dapan(){
        ModelAndView modelAndView=new ModelAndView("dapan");
        modelAndView.addObject("dapan","/zMain/file/sh_000001");
        return modelAndView;
    }


    @GetMapping(value = "/concept")
    public ModelAndView concept(String name){
        ModelAndView modelAndView=new ModelAndView("conceptDetail");
        modelAndView.addObject("concept",name);
        modelAndView.addObject("conceptUrl","/zMain/file/"+name);
        return modelAndView;
    }

    /**
     * key 是独家的秘钥
     * @param day
     * @param key
     * @return
     */
    @GetMapping(value = "/today")
    public ModelAndView today(String day,String key) throws IOException {
        String isMe="";
        ModelAndView modelAndView=new ModelAndView("today");
        List<ShowTimeVo> showTime=stockService.queryShowTime();
        List<CandidateStockPo> result=stockService.stockToday(day,showTime);

        List<CandidateStockPo> list=result.stream().filter(c->c.getZsm()==3).sorted(Comparator.comparing(CandidateStockPo::getPrice)).collect(Collectors.toList());
        List<CandidateStockPo> wenyuRiver=result.stream().filter(c->c.getZsm()==4).sorted(Comparator.comparing(CandidateStockPo::getPrice)).collect(Collectors.toList());
        List<CandidateStockPo> wangjiaodongli=result.stream().filter(c->c.getZsm()==5).sorted(Comparator.comparing(CandidateStockPo::getPrice)).collect(Collectors.toList());
        List<CandidateStockPo> chundongli=result.stream().filter(c->c.getZsm()==6).sorted(Comparator.comparing(CandidateStockPo::getPrice)).collect(Collectors.toList());
        List<CandidateStockPo> shui=result.stream().filter(c->c.getZsm()==2).sorted(Comparator.comparing(CandidateStockPo::getCv)).collect(Collectors.toList());
        List<CandidateStockPo> gu=result.stream().filter(c->c.getZsm()==1).sorted(Comparator.comparing(CandidateStockPo::getCv)).collect(Collectors.toList());
        List<CandidateStockPo> donglifanzhuan=result.stream().filter(c->c.getZsm()==99).collect(Collectors.toList());
        List<CandidateStockPo> chaoji=result.stream().filter(c->c.getZsm()==7).collect(Collectors.toList());
            //上边是温榆河
            wenyuRiver.forEach(c -> {
                c.setWenyuRiver(1);
            });
            wenyuRiver.addAll(list);
            list = wenyuRiver;
        HashMap<String,Integer> integerHashMap=new HashMap<>();
        HashMap<String,Integer> integerHashMapTdx=new HashMap<>();
        HashMap<String,Integer> integerHashMapZQ=new HashMap<>();
        HashMap<String,Integer> integerHashMapWJDL=new HashMap<>();
        for (int i = 0; i < result.size(); i++) {
            //only me and nan
            if (result.get(i).getZsm()>0&&result.get(i).getZsm()<7) {

                if (result.get(i).getZsm()==2) {
                    if (integerHashMap.containsKey(result.get(i).getIndustry())) {
                        integerHashMap.put(result.get(i).getIndustry(), integerHashMap.get(result.get(i).getIndustry()) + 1);
                    } else {
                        integerHashMap.put(result.get(i).getIndustry(), 1);
                    }
                }else if (result.get(i).getZsm()==3){
                    if (integerHashMapTdx.containsKey(result.get(i).getIndustry())) {
                        integerHashMapTdx.put(result.get(i).getIndustry(), integerHashMapTdx.get(result.get(i).getIndustry()) + 1);
                    } else {
                        integerHashMapTdx.put(result.get(i).getIndustry(), 1);
                    }
                }else if (result.get(i).getZsm()==1){
                    if (integerHashMapZQ.containsKey(result.get(i).getIndustry())) {
                        integerHashMapZQ.put(result.get(i).getIndustry(), integerHashMapZQ.get(result.get(i).getIndustry()) + 1);
                    } else {
                        integerHashMapZQ.put(result.get(i).getIndustry(), 1);
                    }
                }else if (result.get(i).getZsm()==5 ||result.get(i).getZsm()==6){
                    if (integerHashMapWJDL.containsKey(result.get(i).getIndustry())) {
                        integerHashMapWJDL.put(result.get(i).getIndustry(), integerHashMapWJDL.get(result.get(i).getIndustry()) + 1);
                    } else {
                        integerHashMapWJDL.put(result.get(i).getIndustry(), 1);
                    }
                }
            }
        }
        List<IndustryStastiscVo> industryStastiscVos =  new ArrayList<>();
        List<IndustryStastiscVo> industryStastiscVosTdx =  new ArrayList<>();
        List<IndustryStastiscVo> industryStastiscVosZQ =  new ArrayList<>();
        List<IndustryStastiscVo> industryStastiscVosWJDL =  new ArrayList<>();

        Iterator iteratorWJDL=integerHashMapWJDL.entrySet().iterator();
        while (iteratorWJDL.hasNext()){
            Map.Entry entry= (Map.Entry) iteratorWJDL.next();
            industryStastiscVosWJDL.add(new IndustryStastiscVo(entry.getKey(),entry.getValue()));
        }
        if (!CollectionUtils.isEmpty(industryStastiscVosWJDL)){
            industryStastiscVosWJDL=industryStastiscVosWJDL.stream()
                    .sorted(Comparator.comparing(IndustryStastiscVo::getCount).reversed())
                    .collect(Collectors.toList());
        }



        Iterator iterator=integerHashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            industryStastiscVos.add(new IndustryStastiscVo(entry.getKey(),entry.getValue()));
        }
        if (!CollectionUtils.isEmpty(industryStastiscVos)){
            industryStastiscVos=industryStastiscVos.stream()
                    .sorted(Comparator.comparing(IndustryStastiscVo::getCount).reversed())
                    .collect(Collectors.toList());
        }

        iterator=integerHashMapTdx.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            industryStastiscVosTdx.add(new IndustryStastiscVo(entry.getKey(),entry.getValue()));
        }
        if (!CollectionUtils.isEmpty(industryStastiscVosTdx)){
            industryStastiscVosTdx=industryStastiscVosTdx.stream()
                    .sorted(Comparator.comparing(IndustryStastiscVo::getCount).reversed())
                    .collect(Collectors.toList());
        }

        iterator=integerHashMapZQ.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            industryStastiscVosZQ.add(new IndustryStastiscVo(entry.getKey(),entry.getValue()));
        }
        if (!CollectionUtils.isEmpty(industryStastiscVosZQ)){
            industryStastiscVosZQ=industryStastiscVosZQ.stream()
                    .sorted(Comparator.comparing(IndustryStastiscVo::getCount).reversed())
                    .collect(Collectors.toList());
        }


        String clickTime = DateUtil.formatTime(new Date());
        if (!CollectionUtils.isEmpty(result)){
            clickTime=result.get(0).getCollect_date();
        }
        //概念板块，待优化
//        String path="C:\\Users\\Administrator\\PycharmProjects\\sMain\\src\\industry_result.txt";
//        JSONObject jsonObject= JSON.parseObject(readJsonFile(new File(path)));
//        JSONArray buyList=jsonObject.getJSONArray("buy");
        List<String> buys=new ArrayList<>();
//        for (int i = 0; i < buyList.size(); i++) {
//            buys.add((String) buyList.get(i));
//        }

        modelAndView.addObject("industry",industryStastiscVos);
        modelAndView.addObject("industryWJDL",industryStastiscVosWJDL);
        modelAndView.addObject("industryTdx",industryStastiscVosTdx);
        modelAndView.addObject("industryZQ",industryStastiscVosZQ);
        modelAndView.addObject("wjdl",wangjiaodongli);
        modelAndView.addObject("dl",chundongli);
        modelAndView.addObject("donglifanzhuan",donglifanzhuan);
        modelAndView.addObject("chaoji",chaoji);
        modelAndView.addObject("list",list);
        modelAndView.addObject("shui",shui);
        modelAndView.addObject("gu",gu);
        modelAndView.addObject("time",showTime);
        modelAndView.addObject("isMe",isMe);
        modelAndView.addObject("clickTime",clickTime);
        modelAndView.addObject("concept",buys);
        return modelAndView;
    }

    public static String readJsonFile(File jsonFile) {
        String jsonStr = "";
        try {
            //File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(value = "/detail/{code}")
    public ModelAndView detail(@PathVariable String code, HttpServletResponse response) throws IOException {
        String dfcf="http://quote.eastmoney.com/";
        String dfcfCode=code.replace("_","");
        String dfcfDetail=dfcf+dfcfCode+".html";
        String filePath=myConfig.getBasePath()+"/temp/"+code.replace("_",".")+".png";
        File file=new File(filePath);
        if (!file.exists()){
            //重定向到本地
            ImageByPython.draw(code.replace("_","."));
//            response.sendRedirect(dfcfDetail);
        }
        CandidateStockPo po=stockService.queryByCode(code.replace("_","."));
        po.setImage("/zMain/file/"+po.getCode().replace(".","_"));
        ModelAndView modelAndView=new ModelAndView("detail");
        modelAndView.addObject("po",po);
        modelAndView.addObject("dfcf",dfcfDetail);
        return modelAndView;
    }

    @GetMapping(value = "/about")
    public ModelAndView about(String key){
        ModelAndView modelAndView=null;
        if (StringUtils.isEmpty(key)) {
            modelAndView = new ModelAndView("aboutP");
        }else{
            modelAndView = new ModelAndView("aboutP");
        }
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "/tongji")
    public Object tongji(String key){
        key="P@$$Word";
        return stockService.tongji(key);
    }

    public static void main(String[] args) {
        String path="C:\\Users\\tianjingle\\PycharmProjects\\sMain\\src\\industry_result.txt";
        JSONObject jsonObject= JSON.parseObject(readJsonFile(new File(path)));
        JSONArray buyList=jsonObject.getJSONArray("buy");
        List<String> buys=new ArrayList<>();
        for (int i = 0; i < buyList.size(); i++) {
            buys.add((String) buyList.get(i));
        }
        System.out.println(buys);
    }
}
