package com.scaffold.zmain.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.locks.LockSupport;

/**
 * @author jingletian
 * @date 2022/4/19 10:35
 */
public class ImageByPython {

    public static void draw(String code) {
        Process proc;
        try {
//            String[] pyScript = new String[] { "python", "C:\\Users\\Administrator\\PycharmProjects\\sMain\\src\\CoreJava.py", String.valueOf(code)};
            String[] pyScript = new String[] { "python", "C:\\Users\\tianjingle\\PycharmProjects\\sMain\\src\\CoreJava.py", String.valueOf(code)};
            proc = Runtime.getRuntime().exec(pyScript);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ImageByPython.draw("sz.300513");
    }
}

