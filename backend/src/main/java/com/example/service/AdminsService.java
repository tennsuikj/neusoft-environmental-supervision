package com.example.service;


import com.example.entity.Admins;
import com.example.mapper.AdminsMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
//创建service并标注为springboot中的bean

@Service
public class AdminsService {

    @Resource
    private AdminsMapper adminsMapper;

    public Admins getAdminsByCode(String adminCode) {
        return adminsMapper.getAdminsByCode(adminCode);
    }

    public Admins login(Admins admins) {
        return adminsMapper.login(admins);
    }

    public void updatePassword(Integer id, String newPassword) {
        adminsMapper.updatePassword(id, newPassword);
    }
}
