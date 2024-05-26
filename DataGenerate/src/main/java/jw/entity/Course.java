package jw.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Course {
    public String C_id;
    public String C_name;
    public Course(String c_id,String c_name) {
        C_id = c_id;
        C_name = c_name;
    }

    public Course(String c_name) {
        C_name = c_name;
    }
}
