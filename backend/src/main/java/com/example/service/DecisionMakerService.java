package com.example.service;

import com.example.entity.DecisionMaker;
import com.example.mapper.DecisionMakerMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DecisionMakerService {

    @Resource
    private DecisionMakerMapper decisionMakerMapper;

    public DecisionMaker login(DecisionMaker decisionMaker) {
        return decisionMakerMapper.login(decisionMaker);
    }
}
