package com.example.controller;

import com.example.common.Result;
import com.example.entity.GridCity;
import com.example.entity.GridProvince;
import com.example.service.GridCityService;
import com.example.service.GridProvinceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gridCity")
public class GridCityController {

    @Resource
    private GridCityService gridCityService;

    @Resource
    private GridProvinceService gridProvinceService;

    /**
     * 返回省份及其下辖城市列表（前端省市联动下拉框用）
     */
    @GetMapping("/getProvinceAndCity")
    public Result getProvinceAndCity() {
        List<GridProvince> provinces = gridProvinceService.selectAll();
        List<GridCity> cities = gridCityService.selectAll();

        List<Map<String, Object>> result = new ArrayList<>();
        for (GridProvince p : provinces) {
            Map<String, Object> item = new HashMap<>();
            item.put("provinceId", p.getProvinceId());
            item.put("provinceName", p.getProvinceName());

            List<Map<String, Object>> cityList = new ArrayList<>();
            for (GridCity c : cities) {
                if (c.getProvinceId().equals(p.getProvinceId())) {
                    Map<String, Object> cityMap = new HashMap<>();
                    cityMap.put("cityId", c.getCityId());
                    cityMap.put("cityName", c.getCityName());
                    cityList.add(cityMap);
                }
            }
            item.put("cityList", cityList);
            result.add(item);
        }
        return Result.success(result);
    }
}
