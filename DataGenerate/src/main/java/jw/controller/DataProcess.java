package jw.controller;

import jw.DB.DBOperation;
import jw.DB.StudentData;
import jw.entity.*;
import jw.service.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DataProcess {
    private ArrayList<Student> StudentList = new ArrayList<Student>();
    private ArrayList<Course> CourseList = new ArrayList<Course>();
    private ArrayList<Teacher> TeacherList = new ArrayList<Teacher>();
    private ArrayList<TeachClass> TeachClassList = new ArrayList<TeachClass>();
    private int num;
    private Random random=new Random();

    public void Stu_Generate(String str){
        if(str.equals("0")){
            System.out.println("随机生成学生");
            Random random=new Random();
            num = 100+random.nextInt(50);
        }else{
            System.out.println("请输入生成学生数量");
            Scanner scanner=new Scanner(System.in);
            num=scanner.nextInt();
        }
        Stu_Gen_Iden stuGenIden = new Stu_Gen_Iden();
        StudentList = stuGenIden.DataGenerate(num);
        DBOperation dbOperation=new DBOperation();
        for(Student s:StudentList){
            dbOperation.addStudent(s);
        }
    }
    public void Cou_Generate(String str){
        if(str.equals("0")){
            System.out.println("生成随机课程");
            Random random=new Random();
            num = 3+random.nextInt(5);
        }else{
            System.out.println("请输入课程数量");
            Scanner scanner=new Scanner(System.in);
            num=scanner.nextInt();
        }
        Cou_Gen_Iden couGenIden = new Cou_Gen_Iden();
        CourseList = couGenIden.DataGenerate(num);
    }
    public void Tea_Generate(String str){
        if(str.equals("0")){
            System.out.println("随机生成教师");
            Random random=new Random();
            num = 6+random.nextInt(10);
        }else{
            System.out.println("请输入生成教师数量");
            Scanner scanner=new Scanner(System.in);
            num=scanner.nextInt();
        }
        Tea_Gen_Iden teaGenIden = new Tea_Gen_Iden();
        TeacherList = teaGenIden.DataGenerate(num);
    }
    public void Class_Generate(){
        System.out.println("生成教学班级并匹配课程");
        Class_Gen_Iden classGenIden = new Class_Gen_Iden();
        TeachClassList = classGenIden.CourseDivide(CourseList);
    }
    public void Student_Choose(){
        System.out.println("学生选课");
        Cou_Gen_Iden couGenIden=new Cou_Gen_Iden();
        for(Student s:StudentList){//对于每个学生，判断该学生选修课程数是否达标
            while(s.CourseList.size()<3+random.nextInt(3)){
                int t=random.nextInt(TeachClassList.size());//随机选择教学班
                if(!s.CourseList.contains(TeachClassList.get(t).course.C_id)){//判断该学生是否选择过该教学班课程
                    s.CourseList.add(TeachClassList.get(t).course.C_id);
                    int index=couGenIden.GetCourseIndex(TeachClassList.get(t).course.C_name);
                    s.S_ClassList[index]=TeachClassList.get(t).getTC_id();//学生添加该课程id
                    int temp=TeachClassList.get(t).getNum();
                    TeachClassList.get(t).setNum(temp+1);//教学班添加该学生
                    TeachClassList.get(t).StudentList.add(s);
                }

                if(s.CourseList.size()==CourseList.size()){break;}
            }
        }
        for(TeachClass t:TeachClassList){//教学班人数不少于20人
            int classAddNum = random.nextInt(10);
            while(t.getNum()<20+classAddNum){//当教学班人数不达标时，选择部分学生选修
                int r=random.nextInt(StudentList.size());
                while(StudentList.get(r).CourseList.contains(t.course.C_id)){
                    r=random.nextInt(StudentList.size());
                }
                StudentList.get(r).CourseList.add(t.course.C_id);
                int index=couGenIden.GetCourseIndex(t.course.C_name);
                StudentList.get(r).S_ClassList[index]=t.getTC_id();
                int temp=t.getNum();
                t.setNum(temp+1);
                t.StudentList.add(StudentList.get(r));
            }
        }
    }
    public void Teacher_Choose(){

        System.out.println("教师选课");
        int TeacherAdd=0;
        DBOperation dbOperation = new DBOperation();
        Teacher teacher = new Teacher();
        for(TeachClass s:TeachClassList){//对于每个教师，判断是否交该门课程，每个教师相同课程最多教一个班
            while(TeacherList.get(TeacherAdd).CourseList.contains(s.course)){
                TeacherAdd+=1;
                if(TeacherAdd>=TeacherList.size()-1) TeacherAdd=0;
            }
            s.TeacherList.add(TeacherList.get(TeacherAdd));
            TeacherList.get(TeacherAdd).CourseList.add(s.course);
            //
            teacher = TeacherList.get(TeacherAdd);
            dbOperation.TeacherToClass(s.getTC_id(),teacher.getT_id(),teacher.getT_name(),s.getCourse().getC_name());
            TeacherAdd+=3;
            if(TeacherAdd>=TeacherList.size()-1) TeacherAdd=0;
        }
    }
    public void Grade_Generate(){
        System.out.println("随机生成学生成绩");

        DBOperation dbOperation=new DBOperation();

        for(Student s:StudentList){
            double[] temp=new double[8];//课程成绩数组
            for(int i=0;i<s.CourseList.size();i++) {
                int CourseNum = Integer.parseInt(s.CourseList.get(i).substring(2));
                Grade_Gen gradeGen=new Grade_Gen();
                Grade grade=gradeGen.RandomScore();
                temp[CourseNum-1]=grade.Syn_Grade;

                grade.course=CourseList.get(CourseNum-1);
                s.GradeList.add(grade);//向学生对象中传入成绩，并添加至成绩表中
                dbOperation.addGrade(new StudentData(s.getS_id(),s.getS_name(),grade.course.C_name,temp[CourseNum-1],s.S_ClassList[CourseNum-1]));
            }

        }
    }
    public void class_Grade(){
        System.out.println("请输入教学班号和查询类型(id/grade)");
        Scanner scanner=new Scanner(System.in);
        String num=scanner.nextLine();
        String[]arg=num.split(" ");
        DBOperation dbOperation=new DBOperation();
        dbOperation.selectAsClass(arg);
    }
    public void Grade_Area(){
        System.out.println("分数段分布状态");
        DBOperation dbOperation=new DBOperation();
        dbOperation.GradeArea();
    }
    public void Person_grade(){
        System.out.println("学生成绩查询，请输入查询的学号/姓名");
        Scanner scanner=new Scanner(System.in);
        String num=scanner.nextLine();
        DBOperation dbOperation=new DBOperation();
        dbOperation.OneGrade(num);
    }
    public void Make_List(){
        System.out.println("排名显示(学号/科目成绩/总成绩) 请输入id/科目名/total");
        DBOperation dbOperation= new DBOperation();
        Scanner scanner=new Scanner(System.in);
        String num=scanner.nextLine();
        if(num.equals("id")){
            dbOperation.GradeByNumber();
        }else if(num.equals("total")){
            dbOperation.TotalGrade();
        }else{
            dbOperation.GradeByCourse(num);
        }
    }

}
