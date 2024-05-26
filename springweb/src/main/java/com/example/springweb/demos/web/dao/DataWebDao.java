package com.example.springweb.demos.web.dao;

import com.example.springweb.demos.web.entity.GradeAreaInfo;
import com.example.springweb.demos.web.entity.gradeInfo;

import java.util.List;
import java.util.Map;

public interface DataWebDao {
    public boolean loginConfirm(String username,String password,String role);
    public List<gradeInfo> getStuGrade(String stuId);
    public void logoutAct(String stuId,Integer role);
    public List<gradeInfo> getClassGrade(String teachclass,String type);
    public List<Map<String, Object>> getClassList(String username);
    public void updateGrade(String stuId,String teachclass,Integer grade);
    public List<String> getCourseList();
    public Map<String, String> getTCList();
    public void addCourse(String courseName);
    public void deleteCourse(String courseName);
    public void addTC(String classId,String courseName);
    public void deleteTC(String classId);
    public void addUser(String username,String password,String role);
    public List<GradeAreaInfo> generateGradeArea();
    public List<gradeInfo> getGradeRanking(String type);
}
