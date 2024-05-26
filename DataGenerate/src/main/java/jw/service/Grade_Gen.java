package jw.service;

import jw.entity.Grade;

import java.util.Random;

public class Grade_Gen {
    Random random=new Random();

    public Grade RandomScore(){
        Grade grade=new Grade();
        grade.Exp_Grade=random.nextInt(50)+50;
        grade.Nor_Grade=random.nextInt(50)+50;
        grade.Mid_Grade=random.nextInt(50)+50;
        grade.End_Grade=random.nextInt(50)+50;
        grade.Syn_Grade=(grade.Exp_Grade+grade.Nor_Grade+grade.Mid_Grade+grade.End_Grade)/4;
//        grade.Syn_Grade = Math.round(grade.Syn_Grade * 100.0) / 100.0;
        return grade;
    }

}
