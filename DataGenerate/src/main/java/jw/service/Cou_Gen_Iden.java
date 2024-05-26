package jw.service;

import jw.entity.Course;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Cou_Gen_Iden {
    String[] ArrayCourseList={"JAVA企业级应用","网络空间安全概论","计算机图形学","编译原理","自然语言处理","文明经典","概率论","智能系统"};
    public ArrayList DataGenerate(int num){
        ArrayList<Course> CourseList=new ArrayList<Course>();
        for(int i=0;i<num;i++){
            Course course = new Course("CS"+String.format("%03d",i+1),ArrayCourseList[i]);
            //确定开课班级数，每门课程至少两门课
            CourseList.add(course);
        }
        return CourseList;
    }
    public int GetCourseIndex(String str){
        int CourseIndex = 0;
        for(String s:ArrayCourseList){
            if(s.equals(str))break;
            CourseIndex++;
        }
        return CourseIndex;
    }
}
