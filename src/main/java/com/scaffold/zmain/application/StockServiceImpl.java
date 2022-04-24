package com.scaffold.zmain.application;

import com.scaffold.zmain.api.vo.ShowTimeVo;
import com.scaffold.zmain.api.vo.TongJiVo;
import com.scaffold.zmain.application.service.StockService;
import com.scaffold.zmain.config.MyConfig;
import com.scaffold.zmain.db.noun.CandidateStockPo;
import com.scaffold.zmain.db.noun.mapper.CandidateStockMapper;
import com.scaffold.zmain.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author tianjl
 * @Date 2021/10/27 14:17
 * @Discription disc
 */
@Service
public class StockServiceImpl implements StockService {


    public static ConcurrentMap<String,Object> cache=new ConcurrentHashMap<>();

    public static ConcurrentMap<String,List<ShowTimeVo>> showTimeCache=new ConcurrentHashMap<>();

    public static ConcurrentMap<String,TongJiVo> tongjiCache=new ConcurrentHashMap<>();



    @Autowired
    private CandidateStockMapper candidateStockMapper;

    @Autowired
    private MyConfig myConfig;


    public Map<String,String> gudongMap() throws IOException {
        Map<String,String> map=new HashMap<>();
        if (myConfig.getGudong()!=null&&new File(myConfig.getGudong()).exists()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(myConfig.getGudong()), "utf-8"));
            String t;
            while ((t = reader.readLine()) != null) {
                try {
                    if (t != null && !t.trim().isEmpty()) {
                        if (t.startsWith(",")){
                            continue;
                        }
                        String[] lines=t.split(",");
                        if (lines.length>2){
                            String code=lines[1].trim();
                            System.out.println(code);
                            map.put(code,"");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),22,0,0);  //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52);
        Date updateDate=calendar.getTime();//date就是你需要的时间
        System.out.println(DateUtil.formatDateTime(updateDate));
    }

    @Override
    public List<CandidateStockPo> stockToday(String day, List<ShowTimeVo> showTime) throws IOException {
        int index=0;
        if (StringUtils.isEmpty(day)){
            day="";
        }
        //当前时间不是工作日
        switch (day){
            case "1":
                //前一天
                index=1;
                break;
            case "2":
                index=2;
                break;
            default:
                index=0;
                break;
        }
        String key=showTime.get(index).getTime();
        //从cache中获取
        if (cache.containsKey(key)){
            return (List<CandidateStockPo>) cache.get(key);
        }
        //获取指定日期的
        CandidateStockPo po=candidateStockMapper.todayDate(key);
        if (Objects.isNull(po)){
            return new ArrayList<>();
        }
        String currentDate=po.getCollect_date();
        List<CandidateStockPo> list= candidateStockMapper.findAll(currentDate);


        Map<String,String> gudongMap=gudongMap();
        list.forEach(c->{
            String code=c.getCode();
            System.out.println(code);
            if (code.contains(".")){
                code=code.substring(3);
            }
            if (gudongMap.containsKey(code)){
                c.setGudong(1);
            }
            c.setShowCode(c.getCode().replace(".","_"));
        });
        cache.putIfAbsent(key,list);
        return list;
    }

    @Override
    public CandidateStockPo queryByCode(String code) {
        CandidateStockPo po=candidateStockMapper.queryByCode(code);
        if (Objects.isNull(po)){
            po=new CandidateStockPo();
            po.setCode("eroor");
            po.setImage("/zMain/file/zMain");
            po.setIndustry("");
        }
        return  po;
    }

    @Override
    public List<ShowTimeVo> queryShowTime() {
        String key="";
        Calendar calendar=Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE),21,0,0);  //年月日  也可以具体到时分秒如calendar.set(2015, 10, 12,11,32,52);
        Date updateDate=calendar.getTime();//date就是你需要的时间
        //当日的更新时间点判断，到底走前一天还是当天的
        String dateString=DateUtil.formatDate(new Date());
        if (updateDate.before(new Date())){
            //当前晚上22点之后就显示当天
            key=dateString;
        }else{
            //显示前一天
            key=DateUtil.formatDate(DateUtil.afterDay(new Date(),-1));
        }
        if (showTimeCache.containsKey(key)){
            return showTimeCache.get(key);
        }else{
            List<ShowTimeVo> showTimeVos=new ArrayList<>();
            List<String> showTime=candidateStockMapper.timeLimit(3);
            for (int i = 0; i < showTime.size(); i++) {
                showTimeVos.add(new ShowTimeVo(showTime.get(i),i));
            }
            showTimeCache.putIfAbsent(key,showTimeVos);
            tongjiCache.clear();
            doTongji();
            return showTimeVos;
        }
    }

    private void doTongji() {
        String date;
        List<Double> y=new ArrayList<>();
        List<Double> s=new ArrayList<>();
        List<Double> h=new ArrayList<>();
        List<Double> w=new ArrayList<>();
        List<Double> wjdl=new ArrayList<>();
        List<Double> dl=new ArrayList<>();
        List<Double> fanzhuandongli=new ArrayList<>();
        Integer [] skip=new Integer[]{6,8,11,15,20,26,33,40,60};

        for (int i = 0; i < skip.length; i++) {
            date=DateUtil.formatDate(DateUtil.afterDay(new Date(),-skip[i]));
            List<CandidateStockPo> list= candidateStockMapper.findRecent(date);

            int fsx=0;
            int fsxtotal=0;


            int hwj=0;
            int hwjtotal=0;
            int ygzq=0;
            int ygzqtotal=0;
            int wyh=0;
            int wyhtotal=0;
            int mywjdl=0;
            int wjdltotal=0;
            int mydl=0;
            int mydltotal=0;
            int myfanzhuandongli=0;
            int myfanzhuandonglitotal=0;
            for (int j = 0; j < list.size(); j++) {
                switch (list.get(j).getZsm()){
                    case 1:
                        if (list.get(j).getProfit()>0){
                            ygzq=ygzq+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            ygzqtotal = ygzqtotal + 1;
                        }
                        break;
                    case 2:
                        if (list.get(j).getProfit()>0){
                            fsx=fsx+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            fsxtotal = fsxtotal + 1;
                        }
                        break;
                    case 3:
                        if (list.get(j).getProfit()>0){
                            hwj=hwj+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            hwjtotal = hwjtotal + 1;
                        }
                        break;
                    case 4:
                        if (list.get(j).getProfit()>0){
                            wyh=wyh+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            wyhtotal = wyhtotal + 1;
                        }
                        break;
                    case 5:
                        if (list.get(j).getProfit()>0){
                            mywjdl=mywjdl+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            wjdltotal = wjdltotal + 1;
                        }
                        break;
                    case 6:
                        if (list.get(j).getProfit()>0){
                            mydl=mydl+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            mydltotal = mydltotal + 1;
                        }
                        break;
                    case 99:
                        if (list.get(j).getProfit()>0){
                            myfanzhuandongli=myfanzhuandongli+1;
                        }
                        if (list.get(j).getProfit()!=0) {
                            myfanzhuandonglitotal = myfanzhuandonglitotal + 1;
                        }
                        break;
                    default:
                        break;
                }
            }
            if (hwjtotal!=0){
                double d = 1.0*hwj/hwjtotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                h.add(get_double);
            }else{
                h.add(0.0);
            }
            if (fsxtotal!=0){
                double d = 1.0*fsx/fsxtotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                s.add(get_double);
            }else{
                s.add(0.0);
            }
            if (ygzqtotal!=0){
                double d = 1.0*ygzq/ygzqtotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                y.add(get_double);
            }else{
                y.add(0.0);
            }
            if (wyhtotal!=0){
                double d = 1.0*wyh/wyhtotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                w.add(get_double);
            }else{
                w.add(0.0);
            }

            if (wjdltotal!=0){
                double d = 1.0*mywjdl/wjdltotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                wjdl.add(get_double);
            }else{
                wjdl.add(0.0);
            }
            if (mydltotal!=0){
                double d = 1.0*mydl/mydltotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                dl.add(get_double);
            }else{
                dl.add(0.0);
            }

            if (myfanzhuandonglitotal!=0){
                double d = 1.0*myfanzhuandongli/myfanzhuandonglitotal;
                BigDecimal bd = new BigDecimal(d);
                BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                Double get_double=Double.parseDouble(bd2.toString());
                fanzhuandongli.add(get_double);
            }else{
                fanzhuandongli.add(0.0);
            }
        }
        List<Integer> times=Stream.of(skip).collect(Collectors.toList());

        TongJiVo vo=new TongJiVo();
        vo.setHistory(s);
        vo.setTime(times);
        tongjiCache.putIfAbsent("shui", vo);

        vo=new TongJiVo();
        vo.setHistory(h);
        vo.setTime(times);
        tongjiCache.putIfAbsent("hao", vo);

        vo=new TongJiVo();
        vo.setHistory(y);
        vo.setTime(times);
        tongjiCache.putIfAbsent("yi", vo);

        vo=new TongJiVo();
        vo.setHistory(w);
        vo.setTime(times);
        tongjiCache.putIfAbsent("wen", vo);

        vo=new TongJiVo();
        vo.setHistory(wjdl);
        vo.setTime(times);
        tongjiCache.putIfAbsent("wjdl", vo);

        vo=new TongJiVo();
        vo.setHistory(dl);
        vo.setTime(times);
        tongjiCache.putIfAbsent("dl", vo);

        vo=new TongJiVo();
        vo.setHistory(fanzhuandongli);
        vo.setTime(times);
        tongjiCache.putIfAbsent("fanzhuandongli", vo);
    }

    @Override
    public List<TongJiVo> tongji(String key) {
        if (!tongjiCache.containsKey("hao")){
            doTongji();
        }

        List<TongJiVo> tongJiVos=new ArrayList<>();
        tongJiVos.add(tongjiCache.get("hao"));
        tongJiVos.add(tongjiCache.get("shui"));
        tongJiVos.add(tongjiCache.get("yi"));
        tongJiVos.add(tongjiCache.get("wen"));
        tongJiVos.add(tongjiCache.get("wjdl"));
        tongJiVos.add(tongjiCache.get("dl"));
        tongJiVos.add(tongjiCache.get("fanzhuandongli"));
        return tongJiVos;
    }

    @Override
    public Object fetch(int type, int pageIndex, int pageSize) {
        List<CandidateStockPo> list = candidateStockMapper.findAllTemp();
        switch (type){
            case 0:
                //fa
                return list.size();
            case 1:
                if (pageIndex>=1&&pageSize>=1) {
                    int start = (pageIndex - 1) * pageSize;
                    int end =start+pageSize;
                    if (end>=list.size()){
                        end=list.size()-1;
                    }
                    return list.subList(start,end);
                }
            default:
                return list.size();
        }
    }
}
