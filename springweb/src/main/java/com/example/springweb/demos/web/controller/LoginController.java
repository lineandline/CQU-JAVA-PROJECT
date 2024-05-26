package com.example.springweb.demos.web.controller;

import com.example.springweb.demos.web.cache.InfoCache;
import com.example.springweb.demos.web.dao.DataWebDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private DataWebDao dwd;

    public LoginController(DataWebDao dwd) {
        this.dwd = dwd;
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        return "login"; // 这里返回的是视图名，比如login.html
    }

    @PostMapping("/index")
    public String showMainPage(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("role") String role, Model model) {
        //判断用户输入是否正确
        if (dwd.loginConfirm(username, password, role)) {
            InfoCache.username = username;
            //用户身份判断
            if(role.equals("student")){
                model.addAttribute("stuGrades", dwd.getStuGrade(username));  // 将学生成绩列表添加到模型中
                return "stupage";
            }else if(role.equals("teacher")){
                model.addAttribute("username",username);
                model.addAttribute("teacherClasses", dwd.getClassList(username));
                return "teapage";
            }else{
                model.addAttribute("username",username);
                model.addAttribute("courseList",dwd.getCourseList());
                model.addAttribute("TCList",dwd.getTCList());
                return "admpage";
            }
        } else {
            return "login";
        }
    }
}
