package jw.entity;

import lombok.Data;

import java.util.ArrayList;
@Data
public class Student {
    String S_id;
    String S_name;
    int S_gender;
    int S_age;
    String S_phone;
    public String[] S_ClassList = new String[8];
    public ArrayList<String> CourseList = new ArrayList<String>();
    public ArrayList<Grade> GradeList = new ArrayList<Grade>();

    public Student(String s_id, String s_name, int s_gender, int s_age, String s_phone) {
        S_id = s_id;
        S_name = s_name;
        S_gender = s_gender;
        S_age = s_age;
        S_phone = s_phone;
    }
}
