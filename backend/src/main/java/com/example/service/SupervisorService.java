package com.example.service;


import com.example.entity.Supervisor;
import com.example.mapper.SupervisorMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SupervisorService {

    @Resource
    private SupervisorMapper supervisorMapper;

    public Supervisor login(Supervisor supervisor) {
        return supervisorMapper.login(supervisor);
    }

    public Supervisor findByTelId(String telId) {
        return supervisorMapper.findByTelId(telId);
    }

    public void register(Supervisor supervisor) {
        supervisorMapper.register(supervisor);
    }

    public void updatePassword(String telId, String newPassword) {
        supervisorMapper.updatePassword(telId, newPassword);
    }
}
