package jw.service;

import jw.entity.Teacher;

import java.util.ArrayList;

public class Tea_Gen_Iden {
    public ArrayList DataGenerate(int num){
        ArrayList<Teacher> TeaList = new ArrayList<Teacher>();
        String name;
        for(int i=0;i<num;i++){
            //复用相同的命名函数
            Stu_Gen_Iden stuGenIden=new Stu_Gen_Iden();
            name=stuGenIden.GetRandomName();
            Teacher teacher = new Teacher(String.format("%05d",i),name);
            TeaList.add(teacher);
        }
        return TeaList;
    }
}
