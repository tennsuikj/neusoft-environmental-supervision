package com.example.mapper;

import com.example.entity.Admins;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AdminsMapper {

    @Select("select * from admins where admin_code = #{adminCode}")
    Admins getAdminsByCode(String adminCode);

    @Select("select * from admins where admin_code = #{adminCode} and password = #{password}")
    Admins login(Admins admins);

    @Update("update admins set password = #{newPassword} where admin_id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("newPassword") String newPassword);
}
