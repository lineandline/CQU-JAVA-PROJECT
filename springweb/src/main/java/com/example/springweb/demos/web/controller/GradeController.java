package com.example.springweb.demos.web.controller;

import com.example.springweb.demos.web.cache.InfoCache;
import com.example.springweb.demos.web.dao.DataWebDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {
    private DataWebDao dwd;

    public GradeController(DataWebDao dwd) {
        this.dwd = dwd;
    }
    @PostMapping("/updateStudentGrade")
    public String updateStudentGrades(@RequestParam("studentId") String studentId, @RequestParam("teachclass") String teachclass, @RequestParam("newGrade1") String newGrade1, @RequestParam("newGrade2") String newGrade2,@RequestParam("newGrade3") String newGrade3,@RequestParam("newGrade4") String newGrade4,Model model) {
        System.out.println(newGrade1+" "+newGrade2+" "+newGrade3+" "+newGrade4);
        Integer grade = Integer.parseInt(newGrade1.replaceAll(",",""))+Integer.parseInt(newGrade2.replaceAll(",",""))+Integer.parseInt(newGrade3.replaceAll(",",""))+Integer.parseInt(newGrade4.replaceAll(",",""));
        dwd.updateGrade(studentId,teachclass,grade/4);
        model.addAttribute("teacherClasses", dwd.getClassList(InfoCache.username));
        model.addAttribute("studentGrades",dwd.getClassGrade(teachclass,"stuid"));
        model.addAttribute("username",InfoCache.username);
        return "teapage";
    }
    @PostMapping("/getGradeArea")
    public String getGradeArea(Model model) {
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("username",InfoCache.username);
        model.addAttribute("gradeAreaList",dwd.generateGradeArea());
        return "admpage";
    }
    @PostMapping("/getGradeRanking")
    public String getGradRanking(@RequestParam("type") String type,@RequestParam("coursename") String coursename,Model model) {
        type = type.equals("course")?coursename:type;
        model.addAttribute("allGradeList",dwd.getGradeRanking(type));
        model.addAttribute("courseList",dwd.getCourseList());
        model.addAttribute("TCList",dwd.getTCList());
        model.addAttribute("gradeAreaList",dwd.generateGradeArea());
        model.addAttribute("username",InfoCache.username);
        return "admpage";
    }
}
