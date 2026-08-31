package com.example.controller;
import com.example.common.Result;
import com.example.entity.Aqi;
import com.example.service.AqiService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//get：查询，post：新增，put：修改，delete：删除
@RestController
@RequestMapping("/aqi")
public class AqiController {

    @Resource
    private AqiService aqiService;

    @GetMapping("/listAqiAll")
    public Result listAqiAll() {
        List<Aqi> list=aqiService.listAqiAll();
        return Result.success(list);
    }

}
