package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.AqiFeedback;
import com.example.service.AqiFeedbackService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

//get：查询，post：新增，put：修改，delete：删除
@RestController
@RequestMapping("/aqiFeedback")
public class AqiFeedbackController {

    @Resource
    private AqiFeedbackService aqiFeedbackService;

    // 保存公众监督员的AQI反馈信息
    @PostMapping("/saveAqiFeedback")
    public Result saveAqiFeedback(@RequestBody AqiFeedback aqiFeedback) {
        aqiFeedback.setAfDate(DateUtil.formatDate(new Date()));
        aqiFeedback.setAfTime(DateUtil.formatTime(new Date()));
        aqiFeedback.setState(0); // 0=待分配
        aqiFeedbackService.saveAqiFeedback(aqiFeedback);
        return Result.success();
    }

    // 根据所属公众监督员手机号返回信息
    @GetMapping("/listAqiFeedbackByTelId")
    public Result listAqiFeedbackByTelId(AqiFeedback aqiFeedback) {
        List<Map<String, Object>> list = aqiFeedbackService.listAqiFeedbackByTelId(aqiFeedback);
        return Result.success(list);
    }

    // 返回所有信息
    @GetMapping("/listAqiFeedbackAll")
    public Result listAqiFeedbackAll(AqiFeedback aqiFeedback) {
        List<Map<String, Object>> list = aqiFeedbackService.listAqiFeedbackAll(aqiFeedback);
        return Result.success(list);
    }

    // 查询AQI反馈信息，并有模糊查询和分页功能
    @GetMapping("/listAqiFeedbackPage")
    public Result listAqiFeedbackPage(AqiFeedback aqiFeedback, @RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Map<String, Object>> pageInfo  = aqiFeedbackService.listAqiFeedbackPage(aqiFeedback,pageNum,pageSize);
        return Result.success(pageInfo);
    }

    // 根据主键查询AQI反馈信息
    @GetMapping("/getAqiFeedbackById")
    public Result getAqiFeedbackById(AqiFeedback aqiFeedback) {
        AqiFeedback result = aqiFeedbackService.getAqiFeedbackById(aqiFeedback);
        return Result.success(result);
    }

    // 根据主键更新AQI反馈信息（更新指派信息）
    @PutMapping("/updateAqiFeedbackAssign")
    public Result updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        int rows = aqiFeedbackService.updateAqiFeedbackAssign(aqiFeedback);
        return Result.success(rows);
    }

    @GetMapping("/listAqiFeedbackByGmId")
    public Result listAqiFeedbackByGmId(AqiFeedback aqiFeedback) {
        List<Map<String, Object>> list = aqiFeedbackService.listAqiFeedbackByGmId(aqiFeedback);
        return Result.success(list);
    }

    @PutMapping("/updateAqiFeedbackState")
    public Result updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        int rows = aqiFeedbackService.updateAqiFeedbackState(aqiFeedback);
        return Result.success(rows);
    }
}
