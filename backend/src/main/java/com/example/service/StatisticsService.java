package com.example.service;

import com.example.entity.Statistics;
import com.example.mapper.StatisticsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @Resource
    private StatisticsMapper statisticsMapper;

    public PageInfo<Statistics> listStatisticsPage(Statistics statistics, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Statistics> list = statisticsMapper.listStatisticsPage(
                statistics.getProvinceId(), statistics.getCityId(), statistics.getConfirmDate());
        return new PageInfo<>(list);
    }

    public List<Map<String, Object>> listAqiDistributeTotalStatis() {
        return statisticsMapper.listAqiDistributeTotalStatis();
    }

    public List<Map<String, Object>> listAqiTrendTotalStatis() {
        return statisticsMapper.listAqiTrendTotalStatis();
    }

    public List<Map<String, Object>> listProvinceItemTotalStatis() {
        return statisticsMapper.listProvinceItemTotalStatis();
    }

    public Integer getAqiCount() {
        return statisticsMapper.getAqiCount();
    }

    public Integer getAqiGoodCount() {
        return statisticsMapper.getAqiGoodCount();
    }

    public Double getProvinceCoverage() {
        Double v = statisticsMapper.getProvinceCoverage();
        return v != null ? v : 0.0;
    }

    public Double getCityCoverage() {
        Double v = statisticsMapper.getCityCoverage();
        return v != null ? v : 0.0;
    }

    public int saveStatistics(Statistics statistics) {
        return statisticsMapper.saveStatistics(statistics);
    }
}
