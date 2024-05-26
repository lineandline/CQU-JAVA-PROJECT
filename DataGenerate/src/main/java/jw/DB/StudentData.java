package jw.DB;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentData {
    private String id;
    private String name;
    private String courseName;
    private double grade;
    private String teachClass;

    public StudentData(String id, String name, String courseName, double grade, String teachClass) {
        this.id = id;
        this.name = name;
        this.courseName = courseName;
        this.grade = grade;
        this.teachClass = teachClass;
    }
}
