package jw.service;

import jw.entity.Course;
import jw.entity.TeachClass;

import java.util.ArrayList;
import java.util.Random;

public class Class_Gen_Iden {
    ArrayList<TeachClass> TchClassList=new ArrayList<TeachClass>();
    Random random=new Random();
    public ArrayList CourseDivide(ArrayList<Course> courseList){
        int classNumber = 1;
        for(int i=0;i<courseList.size();i++){
            String Term = GetRandomTerm();
            int count = 2 + random.nextInt(3);
            while(count>0){
                TeachClass TchClass=new TeachClass(String.format("%05d",classNumber),Term, courseList.get(i));
                TchClassList.add(TchClass);
                count--;
                classNumber++;
            }
        }
        return TchClassList;
    }
    public String GetRandomTerm(){
        String[] TermRan={"春季","秋季"};
        String term = TermRan[random.nextInt(2)];
        return term;
    }
}
