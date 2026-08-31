package com.example.service;

import com.example.entity.GridCity;
import com.example.mapper.GridCityMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridCityService {

    @Resource
    private GridCityMapper gridCityMapper;

    public List<GridCity> listGridCityByProvinceId(GridCity gridCity) {
        return gridCityMapper.listGridCityByProvinceId(gridCity);
    }

    public List<GridCity> selectAll() {
        return gridCityMapper.selectAll();
    }
}
