package com.example.mapper;

import com.example.entity.AqiFeedback;

import java.util.List;
import java.util.Map;

public interface AqiFeedbackMapper {

    int saveAqiFeedback(AqiFeedback aqiFeedback);

    List<Map<String, Object>> listAqiFeedbackAll(AqiFeedback aqiFeedback);

    List<Map<String, Object>> listAqiFeedbackByTelId(AqiFeedback aqiFeedback);

    AqiFeedback getAqiFeedbackById(AqiFeedback aqiFeedback);

    int updateAqiFeedbackAssign(AqiFeedback aqiFeedback);

    List<Map<String, Object>> listAqiFeedbackByGmId(AqiFeedback aqiFeedback);

    int updateAqiFeedbackState(AqiFeedback aqiFeedback);


}
