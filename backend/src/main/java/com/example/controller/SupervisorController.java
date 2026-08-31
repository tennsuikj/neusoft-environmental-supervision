package com.example.controller;

import com.example.service.SupervisorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Resource
    private SupervisorService supervisorService;

}
