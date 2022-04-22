package com.scaffold.zmain.db.noun;

/**
 * @Author tianjl
 * @Date 2021/10/27 9:48
 * @Discription disc
 */

/**
 * id varchar(64) PK
 * code varchar(64)
 * name varchar(64)
 * collect_date varchar(64)
 * industry varchar(64)
 * grad float
 * cv float
 * price float
 * now_price float
 * profit float
 * other varchar(45)
 * is_down_line int
 * zsm int
 * dl int
 */
public class CandidateStockPo {
    private String id;
    private String code;
    private String showCode;
    private String name;
    private String collect_date;
    private String industry;
    private float grad;
    private float cv;
    private float price;
    private float nowPrice;
    private float profit;
    private String other;
    private int isDownLine;
    private int zsm;
    private int dl;
    private int wenyuRiver;
    private int gudong;

    public int getGudong() {
        return gudong;
    }

    public void setGudong(int gudong) {
        this.gudong = gudong;
    }

    public int getWenyuRiver() {
        return wenyuRiver;
    }

    public void setWenyuRiver(int wenyuRiver) {
        this.wenyuRiver = wenyuRiver;
    }

    public String getShowCode() {
        return showCode;
    }

    public void setShowCode(String showCode) {
        this.showCode = showCode;
    }

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollect_date() {
        return collect_date;
    }

    public void setCollect_date(String collect_date) {
        this.collect_date = collect_date;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public float getGrad() {
        return grad;
    }

    public void setGrad(float grad) {
        this.grad = grad;
    }

    public float getCv() {
        return cv;
    }

    public void setCv(float cv) {
        this.cv = cv;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(float nowPrice) {
        this.nowPrice = nowPrice;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getIsDownLine() {
        return isDownLine;
    }

    public void setIsDownLine(int isDownLine) {
        this.isDownLine = isDownLine;
    }

    public int getZsm() {
        return zsm;
    }

    public void setZsm(int zsm) {
        this.zsm = zsm;
    }

    public int getDl() {
        return dl;
    }

    public void setDl(int dl) {
        this.dl = dl;
    }

    @Override
    public String toString() {
        return "CandidateStockPo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", collect_date='" + collect_date + '\'' +
                ", industry='" + industry + '\'' +
                ", grad=" + grad +
                ", cv=" + cv +
                ", price=" + price +
                ", nowPrice=" + nowPrice +
                ", profit=" + profit +
                ", other='" + other + '\'' +
                ", isDownLine=" + isDownLine +
                ", zsm=" + zsm +
                ", dl=" + dl +
                '}';
    }
}
