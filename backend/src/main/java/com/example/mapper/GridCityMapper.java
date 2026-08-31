package com.example.mapper;

import com.example.entity.GridCity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GridCityMapper {

    List<GridCity> listGridCityByProvinceId(GridCity gridCity);

    @Select("select * from grid_city order by city_id")
    List<GridCity> selectAll();
}
