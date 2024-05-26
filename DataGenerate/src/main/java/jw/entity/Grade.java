package jw.entity;

import lombok.Data;

@Data
public class Grade {
    public int Nor_Grade;
    public int Mid_Grade;
    public int Exp_Grade;
    public int End_Grade;
    public int Syn_Grade;
    public Course course;

}
