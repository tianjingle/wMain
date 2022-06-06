package com.scaffold.zmain.api;

/**
 * @author jingletian
 * @date 2022/4/29 9:01
 */
public class Sort {


    public static void main(String[] args) {
        int[] aa=new int[]{1,99,32,45};
        for (int i = 0; i < aa.length; i++) {
            for (int j = i+1; j < aa.length; j++) {
                int temp=aa[i];
                if (temp>aa[j]){
                    aa[i]=aa[j];
                    aa[j]=temp;
                }
            }
        }
        for (int i = 0; i < aa.length; i++) {
            System.out.println(aa[i]);
        }
    }
}
