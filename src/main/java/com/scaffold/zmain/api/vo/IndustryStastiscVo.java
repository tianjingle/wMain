package com.scaffold.zmain.api.vo;

/**
 * @Author tianjl
 * @Date 2021/10/29 19:03
 * @Discription disc
 */
public class IndustryStastiscVo {

    private String industry;

    private int count;

    public IndustryStastiscVo(Object industry, Object count) {
        this.industry = (String) industry;
        this.count = (int) count;
    }


    public String getIndustry() {
//        String temp="'"+this.industry+"'<span class='badge'>"+this.count+"</span>";
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
