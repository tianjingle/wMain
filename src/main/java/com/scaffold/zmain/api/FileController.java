package com.scaffold.zmain.api;

import com.scaffold.zmain.config.MyConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.text.MessageFormat;

/**
 * @Author tianjl
 * @Date 2021/10/27 15:00
 * @Discription disc
 */
@Controller
public class FileController {

    /**
     * 系统配置
     */
    @Autowired
    private MyConfig myConfig;
    /*
     * 下载图片
     */
    @GetMapping(value = "/file/{code}")
    public void download(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        code=code.replaceAll("_",".");
        String fileName=code+".png";
        String filePath=myConfig.getBasePath()+"temp\\"+fileName;
        File file=new File(filePath);
        FileInputStream inputStream=null;
        if (file.exists()){
            inputStream=new FileInputStream(file);
        }else{
            inputStream=new FileInputStream(new File(myConfig.getBasePath()+"temp\\zMain.png"));
        }
        response.reset();
        String browser =  request.getHeader("User-Agent");
        // 设置response的Header
        response.addHeader("Content-Disposition", browser.contains("FIREFOX")?
                MessageFormat.format("attachment;fileName={0}", fileName):
                MessageFormat.format("attachment;fileName={0}", URLEncoder.encode(fileName,"utf-8")));
        response.addHeader("Content-Length", String.valueOf(file.length()));
        response.setContentType("application/octet-stream");
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
}
