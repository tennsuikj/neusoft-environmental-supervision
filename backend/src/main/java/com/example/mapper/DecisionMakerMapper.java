package com.example.mapper;

import com.example.entity.DecisionMaker;
import org.apache.ibatis.annotations.Select;

public interface DecisionMakerMapper {

    @Select("select * from decision_maker where username = #{username} and password = #{password}")
    DecisionMaker login(DecisionMaker decisionMaker);
}
