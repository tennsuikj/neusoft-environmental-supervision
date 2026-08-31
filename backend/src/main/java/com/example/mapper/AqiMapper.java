package com.example.mapper;

import com.example.entity.Aqi;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AqiMapper {

    List<Aqi> listAqiAll();
}
