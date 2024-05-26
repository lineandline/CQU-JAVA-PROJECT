package com.example.springweb.demos.web.controller;

import com.example.springweb.demos.web.dao.DataWebDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private DataWebDao dwd;

    public RegisterController(DataWebDao dwd) {
        this.dwd = dwd;
    }
    @PostMapping("/addUser")
    public String showMainPage(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role) {
        dwd.addUser(username,password,role);
        return "login";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }
}
