package com.example.controller;

import com.example.common.Result;
import com.example.entity.Statistics;
import com.example.service.StatisticsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    // 分页查询
    @GetMapping("/listStatisticsPage")
    public Result listStatisticsPage(Statistics statistics,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Statistics> pageInfo = statisticsService.listStatisticsPage(statistics, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    // AQI指数分布统计
    @GetMapping("/listAqiDistributeTotalStatis")
    public Result listAqiDistributeTotalStatis() {
        List<Map<String, Object>> list = statisticsService.listAqiDistributeTotalStatis();
        return Result.success(list);
    }

    // AQI指数趋势统计
    @GetMapping("/listAqiTrendTotalStatis")
    public Result listAqiTrendTotalStatis() {
        List<Map<String, Object>> list = statisticsService.listAqiTrendTotalStatis();
        return Result.success(list);
    }

    // 各省份污染物超标统计
    @GetMapping("/listProvinceItemTotalStatis")
    public Result listProvinceItemTotalStatis() {
        List<Map<String, Object>> list = statisticsService.listProvinceItemTotalStatis();
        return Result.success(list);
    }

    // 检测总数量
    @GetMapping("/getAqiCount")
    public Result getAqiCount() {
        Integer count = statisticsService.getAqiCount();
        return Result.success(count);
    }

    // 良好数量
    @GetMapping("/getAqiGoodCount")
    public Result getAqiGoodCount() {
        Integer count = statisticsService.getAqiGoodCount();
        return Result.success(count);
    }

    // 省会覆盖率
    @GetMapping("/getProvinceCoverage")
    public Result getProvinceCoverage() {
        Double coverage = statisticsService.getProvinceCoverage();
        return Result.success(coverage);
    }

    // 大城市覆盖率
    @GetMapping("/getCityCoverage")
    public Result getCityCoverage() {
        Double coverage = statisticsService.getCityCoverage();
        return Result.success(coverage);
    }

    // 保存统计数据
    @PostMapping("/saveStatistics")
    public Result saveStatistics(@RequestBody Statistics statistics) {
        int rows = statisticsService.saveStatistics(statistics);
        return Result.success(rows);
    }
}
