package com.example.controller;

import com.example.common.Result;
import com.example.entity.Admins;
import com.example.entity.DecisionMaker;
import com.example.entity.GridMember;
import com.example.entity.Supervisor;
import com.example.service.AdminsService;
import com.example.service.DecisionMakerService;
import com.example.service.GridMemberService;
import com.example.service.SupervisorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class WebController {

    @Resource
    private AdminsService adminsService;

    @Resource
    private GridMemberService gridMemberService;

    @Resource
    private SupervisorService supervisorService;

    @Resource
    private DecisionMakerService decisionMakerService;

    @GetMapping("/hello")
    public Result hello() {
        return Result.success("Hello, the server is running!");
    }

    // ==================== 登录接口 ====================

    // 管理员登录
    @PostMapping("/adminsLogin")
    public Result adminsLogin(@RequestBody Admins admins) {
        Admins result = adminsService.login(admins);
        if (result == null) {
            return Result.error("401", "账号或密码错误");
        }
        result.setPassword(null); // 不返回密码
        return Result.success(result);
    }

    // 网格员登录
    @PostMapping("/gridMemberLogin")
    public Result gridMemberLogin(@RequestBody GridMember gridMember) {
        GridMember result = gridMemberService.login(gridMember);
        if (result == null) {
            return Result.error("401", "账号或密码错误");
        }
        result.setPassword(null);
        return Result.success(result);
    }

    // 公众监督员登录
    @PostMapping("/supervisorLogin")
    public Result supervisorLogin(@RequestBody Supervisor supervisor) {
        Supervisor result = supervisorService.login(supervisor);
        if (result == null) {
            return Result.error("401", "账号或密码错误");
        }
        result.setPassword(null);
        return Result.success(result);
    }

    // 决策员登录
    @PostMapping("/decisionMakerLogin")
    public Result decisionMakerLogin(@RequestBody DecisionMaker decisionMaker) {
        DecisionMaker result = decisionMakerService.login(decisionMaker);
        if (result == null) {
            return Result.error("401", "账号或密码错误");
        }
        result.setPassword(null);
        return Result.success(result);
    }

    // ==================== 注册接口 ====================

    // 公众监督员注册
    @PostMapping("/supervisorRegister")
    public Result supervisorRegister(@RequestBody Supervisor supervisor) {
        // 检查手机号是否已注册
        Supervisor exist = supervisorService.findByTelId(supervisor.getTelId());
        if (exist != null) {
            return Result.error("400", "该手机号已注册");
        }
        supervisorService.register(supervisor);
        return Result.success();
    }

    // ==================== 修改密码 ====================

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, Object> params) {
        String role = (String) params.get("role");
        String password = (String) params.get("password");
        String newPassword = (String) params.get("newPassword");

        if (role == null || password == null || newPassword == null) {
            return Result.error("400", "参数不完整");
        }

        switch (role) {
            case "ADMINS": {
                String adminCode = params.get("adminCode") != null ? params.get("adminCode").toString() : null;
                Admins admins = new Admins();
                admins.setAdminCode(adminCode);
                admins.setPassword(password);
                Admins exist = adminsService.login(admins);
                if (exist == null) {
                    return Result.error("401", "原密码错误");
                }
                adminsService.updatePassword(exist.getAdminId(), newPassword);
                return Result.success("密码修改成功，请重新登录");
            }
            case "GRIDMEMBER": {
                String gmCode = params.get("gmCode") != null ? params.get("gmCode").toString() : null;
                GridMember gm = new GridMember();
                gm.setGmCode(gmCode);
                gm.setPassword(password);
                GridMember exist = gridMemberService.login(gm);
                if (exist == null) {
                    return Result.error("401", "原密码错误");
                }
                gridMemberService.updatePassword(exist.getGmId(), newPassword);
                return Result.success("密码修改成功，请重新登录");
            }
            case "SUPERVISOR": {
                String telId = params.get("telId") != null ? params.get("telId").toString() : null;
                Supervisor s = new Supervisor();
                s.setTelId(telId);
                s.setPassword(password);
                Supervisor exist = supervisorService.login(s);
                if (exist == null) {
                    return Result.error("401", "原密码错误");
                }
                supervisorService.updatePassword(exist.getTelId(), newPassword);
                return Result.success("密码修改成功，请重新登录");
            }
            default:
                return Result.error("400", "未知角色");
        }
    }

}
