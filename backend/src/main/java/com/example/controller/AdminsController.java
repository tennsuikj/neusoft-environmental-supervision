package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admins;
import com.example.service.AdminsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//get：查询，post：新增，put：修改，delete：删除
@RestController
@RequestMapping("/admins")
public class AdminsController {

    @Resource
    private AdminsService adminsService;

    @GetMapping("/getAdminsByCode")
    public Result getAdminsByCode(@PathVariable String adminCode) {
        Admins admins=adminsService.getAdminsByCode(adminCode);
        return Result.success(admins);
    }

}
