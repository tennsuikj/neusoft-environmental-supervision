package com.example.mapper;

import com.example.entity.Supervisor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SupervisorMapper {

    @Select("select * from supervisor where tel_id = #{telId} and password = #{password}")
    Supervisor login(Supervisor supervisor);

    @Select("select * from supervisor where tel_id = #{telId}")
    Supervisor findByTelId(String telId);

    @Insert("insert into supervisor (tel_id, password, real_name, birthday, sex) values (#{telId}, #{password}, #{realName}, #{birthday}, #{sex})")
    void register(Supervisor supervisor);

    @Update("update supervisor set password = #{newPassword} where tel_id = #{telId}")
    void updatePassword(@Param("telId") String telId, @Param("newPassword") String newPassword);
}
