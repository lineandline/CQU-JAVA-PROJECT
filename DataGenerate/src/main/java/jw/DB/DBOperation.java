package jw.DB;

import jw.entity.Student;

import java.sql.*;

public class DBOperation {
    //添加学生信息
    public void addStudent(Student student){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //mysql连接
            conn = DBConnect.getConn();
            String sql = "insert into student(id, name, gender, age, phone) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getS_id());
            stmt.setString(2, student.getS_name());
            stmt.setDouble(3,student.getS_gender());
            stmt.setDouble(4,student.getS_age());
            stmt.setString(5,student.getS_phone());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            DBConnect.closeConn(conn, stmt);
        }
    }
    //对学生成绩表添加成绩
    public void addGrade(StudentData studentData){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnect.getConn();
            String sql = "insert into coursegrade(stuid, name, coursename, grade,teachclass) values(?,?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentData.getId());
            stmt.setString(2, studentData.getName());
            stmt.setString(3,studentData.getCourseName());
            stmt.setDouble(4,studentData.getGrade());
            stmt.setString(5,studentData.getTeachClass());
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            DBConnect.closeConn(conn, stmt);
        }
    }
    //教学班排序查询
    public void selectAsClass(String[] ClassNum) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String step = ClassNum[1].equals("id") ? "stuid ASC" : "grade DESC";
            //order by子句不允许使用参数指定排序列名，此处不能使用参数占位符
            String sql = "SELECT stuid, name, grade FROM coursegrade WHERE teachclass = ? ORDER BY "+step;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, ClassNum[0]);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("stuid");
                String name = rs.getString("name");
                String grade = rs.getString("grade");
                System.out.println(id + " " + name + " " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //分数段查询
    public void GradeArea() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String sql = "SELECT coursename, " +
                    "SUM(CASE WHEN grade >= 0 AND grade < 60 THEN 1 ELSE 0 END) / COUNT(*) AS rateD,\n" +
                    "SUM(CASE WHEN grade >= 60 AND grade < 70 THEN 1 ELSE 0 END) / COUNT(*) AS rateC,\n" +
                    "SUM(CASE WHEN grade >= 70 AND grade < 85 THEN 1 ELSE 0 END) / COUNT(*) AS rateB,\n" +
                    "SUM(CASE WHEN grade >= 85 AND grade <= 100 THEN 1 ELSE 0 END) / COUNT(*) AS rateA " +
                    "FROM coursegrade GROUP BY coursename";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String coursename = rs.getString("coursename");
                String rateA = rs.getString("rateA");
                String rateB = rs.getString("rateB");
                String rateC = rs.getString("rateC");
                String rateD = rs.getString("rateD");
                System.out.println(coursename + " 优：" + rateA + " 良：" + rateB+" 中："+rateC+" 及格："+rateD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //查询单个学生成绩
    public void OneGrade(String str) {
        Connection conn = null;
        Statement stmt = null;
        boolean firstCycle = true;
        Double totalGrade=0.0;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String sql = "select stuid , name , coursename , grade from coursegrade " +
                    "where stuid = ? or name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, str);//将输入字符串同时在学号和姓名中查询
            preparedStatement.setString(2, str);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String stuid = rs.getString("stuid");
                String name = rs.getString("name");
                String coursename = rs.getString("coursename");
                Double grade = rs.getDouble("grade");
                totalGrade+=grade;
                if(firstCycle) {
                    System.out.println(stuid + " " + name + "\n" +coursename+" "+grade);
                    firstCycle=false;
                }else {
                    System.out.println(coursename+" "+grade);
                }

            }
            System.out.println("totalGrade ="+totalGrade);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //按学号排名
    public void GradeByNumber() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String sql = "select stuid , name , coursename , grade from coursegrade " +
                    " group by stuid,name,coursename,grade";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            String stuId=null;
            while (rs.next()) {
                String stuid = rs.getString("stuid");
                String name = rs.getString("name");
                String coursename = rs.getString("coursename");
                Double grade = rs.getDouble("grade");
                if(!stuid.equals(stuId)){
                    System.out.print("\n"+stuid + " " + name +" "+coursename+" "+grade+" ");
                    stuId=stuid;
                }else{
                    System.out.print(" "+coursename+" "+grade);
                }
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //按科目成绩排名
    public void GradeByCourse(String str) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            String sql = "select stuid , name , grade from coursegrade where coursename = ?" +
                    " order by grade desc";//查询成绩表中选择了该科的学生并按照该科目成绩从高到低排序
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, str);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String stuid = rs.getString("stuid");
                String name = rs.getString("name");
                Double grade = rs.getDouble("grade");
                System.out.println(stuid + " " + name + " "+grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //按总成绩排名
    public void TotalGrade() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            //按照学号将同个学生各个科目的成绩记录分组并求和,最后按总成绩降序排序
            String sql = "select stuid , name , sum(grade) as totalgrade from coursegrade " +
                    "group by stuid,name order by totalgrade DESC";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String stuid = rs.getString("stuid");
                String name = rs.getString("name");
                Double totalgrade = rs.getDouble("totalgrade");
                System.out.println(stuid + " " + name+" "+totalgrade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

    //添加教师教学班级对应表项
    public void TeacherToClass(String TC_id,String Teacher_id,String Teacher_name,String Course_name) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        try {
            conn = DBConnect.getConn();
            //按照学号将同个学生各个科目的成绩记录分组并求和,最后按总成绩降序排序
            String sql = "insert into teachclass(id, teacherid, teachername, coursename) values(?,?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,TC_id);
            stmt.setString(2,Teacher_id);
            stmt.setString(3,Teacher_name);
            stmt.setString(4,Course_name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConn(conn, stmt);
        }
    }

}
