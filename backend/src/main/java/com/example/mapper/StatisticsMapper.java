package com.example.mapper;

import com.example.entity.Statistics;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface StatisticsMapper {

    // 分页查询
    List<Statistics> listStatisticsPage(@Param("provinceId") Integer provinceId,
                                         @Param("cityId") Integer cityId,
                                         @Param("confirmDate") String confirmDate);

    // AQI分布统计
    List<Map<String, Object>> listAqiDistributeTotalStatis();

    // AQI趋势统计
    List<Map<String, Object>> listAqiTrendTotalStatis();

    // 各省份污染物统计
    List<Map<String, Object>> listProvinceItemTotalStatis();

    // 总数
    @Select("select count(*) from statistics")
    Integer getAqiCount();

    // 优良数量 (aqi_id = 1 or 2)
    @Select("select count(*) from statistics where aqi_id in (1, 2)")
    Integer getAqiGoodCount();

    // 省会覆盖率：有统计数据的省会数 / 总省会数
    @Select("select round(count(distinct s.province_id) * 100.0 / (select count(*) from grid_province), 2) from statistics s")
    Double getProvinceCoverage();

    // 大城市覆盖率
    @Select("select round(count(distinct s.city_id) * 100.0 / (select count(*) from grid_city), 2) from statistics s")
    Double getCityCoverage();

    // 保存统计数据
    @Insert("insert into statistics (province_id, city_id, address, so2_value, so2_level, co_value, co_level, spm_value, spm_level, aqi_id, confirm_date, confirm_time, gm_id, fd_id, information) " +
            "values (#{provinceId}, #{cityId}, #{address}, #{so2Value}, #{so2Level}, #{coValue}, #{coLevel}, #{spmValue}, #{spmLevel}, #{aqiId}, #{confirmDate}, #{confirmTime}, #{gmId}, #{fdId}, #{information})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int saveStatistics(Statistics statistics);
}
