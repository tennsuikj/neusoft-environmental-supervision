package com.example.mapper;

import com.example.entity.GridProvince;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GridProvinceMapper {

    @Select("select * from grid_province order by province_id")
    List<GridProvince> selectAll();
}
