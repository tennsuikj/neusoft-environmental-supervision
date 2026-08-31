package com.example.controller;

import com.example.service.GridProvinceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//get：查询，post：新增，put：修改，delete：删除
@RestController
@RequestMapping("/gridProvince")
public class GridProvinceController {

    @Resource
    private GridProvinceService gridProvinceService;

}
