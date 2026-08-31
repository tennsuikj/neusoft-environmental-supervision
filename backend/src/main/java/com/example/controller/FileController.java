package com.example.controller;

//处理文件相关接口

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import com.example.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/files")
public class FileController {
    //获取当前项目的根路径
    //文件上传的目录路径
    private static final String filePath = System.getProperty("user.dir")+"/files/";

    //文件上传接口
    @PostMapping("/upload")
    public Result upload(MultipartFile file){//文件流形式接收前端发来的文件
        String originalFilename = file.getOriginalFilename();//xx.png
        if(!FileUtil.isDirectory(filePath)){//目录不存在，先创建目录
            FileUtil.mkdir(filePath);
        }
        //提供文件存储完整的路径
        //给文件名加一个唯一的标识
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        String realPath = filePath + fileName;//完整的文件路径
        try {
            //文件写入磁盘
            FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500","文件上传失败");
        }
        //返回一个网络链接
        String url = "http://localhost:9090/files/download/"+fileName;
        return Result.success(url);
    }

    //文件下载
    @GetMapping("/download/{fileName}")
    public Result download(@PathVariable String fileName, HttpServletResponse response) {
        try{
            //统一编码
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            String realPath = filePath + fileName;//完整的文件路径
            //获取文件的字节数组
            byte[] bytes = FileUtil.readBytes(realPath);
            os.write(bytes);
            os.flush();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new CustomException("500","文件下载失败");
        }
        return Result.success();
    }

    @PostMapping("/wang/upload")
    public Map<String,Object> wangEditorUpload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();//xx.png
        if(!FileUtil.isDirectory(filePath)){//目录不存在，先创建目录
            FileUtil.mkdir(filePath);
        }
        //提供文件存储完整的路径
        //给文件名加一个唯一的标识
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        String realPath = filePath + fileName;//完整的文件路径
        try {
            //文件写入磁盘
            FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500","文件上传失败");
        }
        //返回一个网络链接
        String url = "http://localhost:9090/files/download/"+fileName;

        Map<String,Object> resMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> urlMap = new HashMap<>();
        urlMap.put("url",url);
        list.add(urlMap);
        resMap.put("errno",0);
        resMap.put("data",list);
        return resMap;
    }
}
