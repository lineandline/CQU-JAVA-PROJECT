package jw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TeachClass {
    String TC_id;
    String TC_term;
    public Course course;
    int num;
    public ArrayList<Student> StudentList=new ArrayList<Student>();
    public ArrayList<Teacher> TeacherList=new ArrayList<Teacher>();

    public TeachClass(String TC_id, String TC_term, Course course) {
        this.TC_id = TC_id;
        this.TC_term = TC_term;
        this.course = course;
    }

}
