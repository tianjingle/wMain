package com.scaffold.zmain.api.vo;

/**
 * @Author tianjl
 * @Date 2021/10/28 22:53
 * @Discription disc
 */
public class ShowTimeVo {

    private String time;

    private int flag;

    private String key="";

    public ShowTimeVo(String time, int flag) {
        this.time = time;
        this.flag = flag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
