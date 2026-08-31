package com.example.service;


import com.example.entity.GridMember;
import com.example.mapper.GridMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GridMemberService {

    @Resource
    private GridMemberMapper gridMemberMapper;

    public GridMember login(GridMember gridMember) {
        return gridMemberMapper.login(gridMember);
    }

    public List<GridMember> listByProvinceIdAndCityId(Integer provinceId, Integer cityId) {
        return gridMemberMapper.listByProvinceIdAndCityId(provinceId, cityId);
    }

    public void updatePassword(String id, String newPassword) {
        gridMemberMapper.updatePassword(id, newPassword);
    }
}
