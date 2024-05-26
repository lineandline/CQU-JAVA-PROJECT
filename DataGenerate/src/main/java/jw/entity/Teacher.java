package jw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Teacher {
    String T_id;
    String T_name;
    public ArrayList<Course> CourseList = new ArrayList<Course>();
    public Teacher(String t_id,String t_name) {
        this.T_id=t_id;
        this.T_name=t_name;
    }

    public Teacher() {

    }
}
