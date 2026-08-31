package com.example.service;

import com.example.entity.AqiFeedback;
import com.example.mapper.AqiFeedbackMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AqiFeedbackService {

    @Resource
    private AqiFeedbackMapper aqiFeedbackMapper;

    public int saveAqiFeedback(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.saveAqiFeedback(aqiFeedback);
    }

    public List<Map<String, Object>> listAqiFeedbackAll(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.listAqiFeedbackAll(aqiFeedback);
    }

    public List<Map<String, Object>> listAqiFeedbackByTelId(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.listAqiFeedbackByTelId(aqiFeedback);
    }

    public PageInfo<Map<String, Object>> listAqiFeedbackPage(AqiFeedback aqiFeedback, Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = aqiFeedbackMapper.listAqiFeedbackAll(aqiFeedback);
        return new PageInfo<>(list);
    }

    public AqiFeedback getAqiFeedbackById(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.getAqiFeedbackById(aqiFeedback);
    }

    public int updateAqiFeedbackAssign(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.updateAqiFeedbackAssign(aqiFeedback);
    }

    public List<Map<String, Object>> listAqiFeedbackByGmId(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.listAqiFeedbackByGmId(aqiFeedback);
    }

    public int updateAqiFeedbackState(AqiFeedback aqiFeedback) {
        return aqiFeedbackMapper.updateAqiFeedbackState(aqiFeedback);
    }
}
