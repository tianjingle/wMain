package com.scaffold.zmain.api.vo;

import java.util.List;

/**
 * @Author tianjl
 * @Date 2021/10/31 17:21
 * @Discription disc
 */
public class TongJiVo {

    private List<Integer> time;

    private List<Double> history;

    public List<Integer> getTime() {
        return time;
    }

    public void setTime(List<Integer> time) {
        this.time = time;
    }

    public List<Double> getHistory() {
        return history;
    }

    public void setHistory(List<Double> history) {
        this.history = history;
    }
}
