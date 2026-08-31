package com.example.controller;

import com.example.common.Result;
import com.example.entity.GridMember;
import com.example.service.GridMemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gridMember")
public class GridMemberController {

    @Resource
    private GridMemberService gridMemberService;

    // 根据省份和城市查询网格员列表
    @GetMapping("/listGridMemberByProvinceIdByCityId")
    public Result listGridMemberByProvinceIdByCityId(@RequestParam Integer provinceId,
                                                      @RequestParam Integer cityId) {
        List<GridMember> list = gridMemberService.listByProvinceIdAndCityId(provinceId, cityId);
        return Result.success(list);
    }
}
