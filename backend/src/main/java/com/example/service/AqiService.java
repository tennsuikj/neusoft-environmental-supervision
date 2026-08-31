package com.example.service;


import com.example.entity.Aqi;
import com.example.mapper.AqiMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
//创建service并标注为springboot中的bean

@Service
public class AqiService {

    @Resource
    private AqiMapper aqiMapper;

    public List<Aqi> listAqiAll() {
        return aqiMapper.listAqiAll();
    }
}
