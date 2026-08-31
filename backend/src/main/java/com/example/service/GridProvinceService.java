package com.example.service;


import com.example.entity.GridProvince;
import com.example.mapper.GridProvinceMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridProvinceService {

    @Resource
    private GridProvinceMapper gridProvinceMapper;

    public List<GridProvince> selectAll() {
        return gridProvinceMapper.selectAll();
    }
}
