package jw.service;

import jw.entity.Student;

import java.util.ArrayList;
import java.util.Random;

public class Stu_Gen_Iden {
    public ArrayList DataGenerate(int num){
        Random random = new Random();

        ArrayList<Student> StuList = new ArrayList<Student>();
        String name,phone;
        for(int i=0;i<num;i++){
            name=GetRandomName();
            phone=GetRandomPhone();
            Student student = new Student(String.format("%05d",i+1),name,random.nextInt(2),18+random.nextInt(6),phone);
            StuList.add(student);
        }
        return StuList;
    }
    String GetRandomName(){
        String secondName = "这是一条毫无意义的句子";
        String firstName = "张刘王李袁陈赵";
        return getStringName(firstName, secondName);
    }
    String GetRandomPhone(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // 随机生成前三位运营商号段
        String[] operators = {"130", "131", "132", "138", "139", "145", "150", "188", "189", "199"};
        sb.append(operators[random.nextInt(operators.length)]);

        // 随机生成后八位数字
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();

    }
    private static String getStringName(String firstName, String secondName) {
        Random random = new Random();
        int t1= firstName.length(),t2= secondName.length(),t3=random.nextInt(100);
        String name=String.valueOf(firstName.charAt(random.nextInt(t1-1)))+ secondName.charAt(random.nextInt(t2-1));
        while(t3>30){
            t3=random.nextInt(Math.round(t3/2));
            name=name+ secondName.charAt(random.nextInt(t2-1));
        }
        return name;
    }
}
