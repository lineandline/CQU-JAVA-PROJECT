package com.example.springweb.demos.web.controller;

import com.example.springweb.demos.web.cache.InfoCache;
import com.example.springweb.demos.web.dao.DataWebDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BasicEntityController {
    private DataWebDao dwd;
    public BasicEntityController(DataWebDao dwd) {
        this.dwd = dwd;
    }
    @PostMapping("/addCourse")
    public String addCourse(@RequestParam("courseName") String courseName, Model model) {
        dwd.addCourse(courseName);
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("username", InfoCache.username);
        return "admpage";
    }
    @PostMapping("/deleteCourse")
    public String deleteCourse(@RequestParam("courseName") String courseName, Model model) {
        dwd.deleteCourse(courseName);
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("username",InfoCache.username);
        return "admpage";
    }
    @PostMapping("/addTC")
    public String addTC(@RequestParam("classId") String classId,@RequestParam("courseName") String courseName, Model model) {
        dwd.addTC(classId,courseName);
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("username",InfoCache.username);
        return "admpage";
    }
    @PostMapping("/deleteTC")
    public String deleteTC(@RequestParam("classId") String classId, Model model) {
        dwd.deleteTC(classId);
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("username",InfoCache.username);
        return "admpage";
    }
    @PostMapping("/getClassMember")
    public String getStudentGrades(@RequestParam("classId") String teachClass,@RequestParam("classType") String classType,Model model) {
        model.addAttribute("username",InfoCache.username);
        model.addAttribute("teacherClasses", dwd.getClassList(InfoCache.username));
        model.addAttribute("studentGrades",dwd.getClassGrade(teachClass,classType));
        return "teapage";
    }
}
