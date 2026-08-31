package com.example.mapper;

import com.example.entity.GridMember;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GridMemberMapper {

    @Select("select * from grid_member where gm_code = #{gmCode} and password = #{password}")
    GridMember login(GridMember gridMember);

    @Select("select * from grid_member where province_id = #{provinceId} and city_id = #{cityId}")
    List<GridMember> listByProvinceIdAndCityId(@Param("provinceId") Integer provinceId, @Param("cityId") Integer cityId);

    @Update("update grid_member set password = #{newPassword} where gm_id = #{id}")
    void updatePassword(@Param("id") String id, @Param("newPassword") String newPassword);
}
