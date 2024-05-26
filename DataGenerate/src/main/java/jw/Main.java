package jw;

import jw.controller.DataProcess;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataProcess dp = new DataProcess();
        System.out.println("基于命令行的学生成绩管理系统\n开始生成数据！");
        System.out.println( "输入 0:自动生成随机数据   1:手动设置数据");
        System.out.println("quit：退出");
        String command = scanner.nextLine();
        boolean dataFlag = true;
        while(!command.equals("quit")){
//            System.out.println("请输入命令：");
            if((command.equals("0")||command.equals("1"))&&dataFlag){
                if(command.equals("1")){
                    System.out.println( "1：随机生成学生"+
                                        "2：课程列表中随机选择课程"+
                                        "3：随机生成教师"+
                                        "4：随机生成教学班级"+
                                        "5：学生选课"+
                                        "6：教师选课"+
                                        "7：学生成绩生成");
                }
                dataFlag = false;
                CommonData(command,dp);
                System.out.println("数据生成成功\n");
                System.out.println( "fun1:教学班成绩查询\n"+
                                    "fun2:分数段分布\n"+
                                    "fun3:查询学生个人成绩\n"+
                                    "fun4:总体排名");
            }else if((command.equals("fun1")||command.equals("fun2")||command.equals("fun3")||command.equals("fun4"))&&!dataFlag){
                CommandSteps(command,dp);
                System.out.println( "\nfun1:教学班成绩查询\n"+
                        "fun2:分数段分布\n"+
                        "fun3:查询学生个人成绩\n"+
                        "fun4:总体排名");
            }else if(command.equals("")){
                System.out.println( "fun1:教学班成绩查询\n"+
                        "fun2:分数段分布\n"+
                        "fun3:查询学生个人成绩\n"+
                        "fun4:总体排名");
            } else{
                System.out.println("输入错误!!!");
            }
            command = scanner.nextLine();
        }

    }
    private static void CommonData(String command, DataProcess dp){
        dp.Stu_Generate(command);//随机生成学生
        dp.Cou_Generate(command);//随机选择相关课程
        dp.Tea_Generate(command);//随机生成教师
        dp.Class_Generate();//随机生成教学班级
        dp.Student_Choose();//学生选课，匹配相关课程（即教学班级）
        dp.Teacher_Choose();//教师匹配教学班级和课程
        dp.Grade_Generate();//学生成绩生成
    }
    private static void CommandSteps(String command, DataProcess dp)  {
        if(command.equals("fun1")){//教学班查询
            dp.class_Grade();
        }else if(command.equals("fun2")){//分数段查询
            dp.Grade_Area();
        }else if(command.equals("fun3")){//学号姓名查询
            dp.Person_grade();
        }else{//全体学生排名显示
            dp.Make_List();
        }
    }

}
