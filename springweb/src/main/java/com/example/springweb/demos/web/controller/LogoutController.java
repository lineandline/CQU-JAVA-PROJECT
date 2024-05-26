package com.example.springweb.demos.web.controller;

import com.example.springweb.demos.web.dao.DataWebDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogoutController {
    private DataWebDao dwd;

    public LogoutController(DataWebDao dwd) {
        this.dwd = dwd;
    }
    @PostMapping("/logout")
    public String logoutStudent(@RequestParam("stuid") String stuId, @RequestParam("role") Integer role) {
        dwd.logoutAct(stuId,role);
        return "login"; // 注销成功后重定向到登录页面
    }
}
