package com.example.springweb.demos.web.impl;

import com.example.springweb.demos.web.dao.DataWebDao;
import com.example.springweb.demos.web.entity.GradeAreaInfo;
import com.example.springweb.demos.web.entity.gradeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Configuration
public class DataWebDaoImpl implements DataWebDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(new DriverManagerDataSource()) ;
    @Override
    public boolean loginConfirm(String username, String password, String role) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ? AND role = ?";
        //0:teacher  1:student  2:admin
        int count=role.equals("student")?0:role.equals("teacher")?1:2;
        count = jdbcTemplate.queryForObject(sql, Integer.class, username, password,count);
        return (count==1);
    }

    @Override
    public List<gradeInfo> getStuGrade(String stuId) {
        String sql = "SELECT * FROM coursegrade WHERE stuid = ?";
        List<gradeInfo> gradeList= jdbcTemplate.query(
                sql,
                new Object[]{stuId},
                (rs, rowNum) -> {
                    gradeInfo entity = new gradeInfo();

                    entity.setStuid(rs.getString("stuid"));
                    entity.setName(rs.getString("name"));
                    entity.setCoursename(rs.getString("coursename"));
                    entity.setGrade(rs.getInt("grade"));
                    entity.setTeachclass(rs.getString("teachclass"));
                    return entity;
                });
        return gradeList;
    }
    @Override
    public void logoutAct(String stuId,Integer role){
        String sql = "DELETE FROM user WHERE username = ? and role = ?";
        jdbcTemplate.update(sql, stuId,role);
    }
    public void updateGrade(String stuId,String teachclass,Integer grade){
        String sql = "UPDATE coursegrade SET grade = ? WHERE stuid = ? and teachclass = ?";
        System.out.println("id为"+stuId+"教学班为"+teachclass);
        jdbcTemplate.update(sql, grade,stuId,teachclass);
    }
    public List<Map<String, Object>> getClassList(String username) {
        String sql = "SELECT id, coursename FROM teachclass WHERE teacherid = ?";
        List<Map<String, Object>> teachClassList = jdbcTemplate.queryForList(sql, new Object[]{username});
        return teachClassList;
    }
    @Override
    public List<gradeInfo> getClassGrade(String teachclass,String type) {
        List<gradeInfo> allStudentGrades = new ArrayList<gradeInfo>();
        // 使用教学班ID在coursegrade表中匹配学生的课程名和课程成绩
        String step = type.equals("stuid") ? "stuid ASC" : "grade DESC";
        String sqlCourseGrade = "SELECT stuid, name, coursename, grade,teachclass FROM coursegrade WHERE teachclass = ? ORDER BY "+step;
        List<Map<String, Object>> studentGrades = jdbcTemplate.queryForList(sqlCourseGrade, new Object[]{teachclass});
        for (Map<String, Object> studentGrade : studentGrades) {
            gradeInfo grade = new gradeInfo();
            grade.setStuid((String) studentGrade.get("stuid"));
            grade.setName((String) studentGrade.get("name"));
            grade.setCoursename((String) studentGrade.get("coursename"));
            grade.setGrade((Integer) studentGrade.get("grade"));
            grade.setTeachclass((String) studentGrade.get("teachclass"));
            allStudentGrades.add(grade);
        }
        return allStudentGrades;
    }
    public List<String> getCourseList(){
        String sql = "SELECT coursename FROM course";
        return jdbcTemplate.queryForList(sql,String.class);
    }
    public Map<String, String> getTCList() {
        String sql = "SELECT id, coursename FROM teachclass";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, String> result = new HashMap<>();
        for (Map<String, Object> row : rows) {
            result.put(row.get("id").toString(), row.get("coursename").toString());
        }
        return result;
    }
    public void addCourse(String courseName){
        String sql = "INSERT INTO course (coursename) VALUES (?)";
        jdbcTemplate.update(sql, courseName);
    }
    public void deleteCourse(String courseName) {
        String sql = "DELETE FROM course WHERE coursename = ?";
        jdbcTemplate.update(sql, courseName);
    }
    public void addTC(String classId,String courseName){
        String sql = "INSERT INTO teachclass (id,coursename) VALUES (?,?)";
        jdbcTemplate.update(sql, classId,courseName);
    }
    public void deleteTC(String classId) {
        String sql = "DELETE FROM teachclass WHERE id = ?";
        jdbcTemplate.update(sql, classId);
    }
    public void addUser(String username,String password,String role){
        int roleNum=role.equals("student")?0:role.equals("teacher")?1:2;
        String sql = "INSERT INTO user (username,password,role) VALUES (?,?,?)";
        jdbcTemplate.update(sql, username,password,roleNum);
    }
    public List<GradeAreaInfo> generateGradeArea() {
        String sql = "SELECT coursename, " +
                "SUM(CASE WHEN grade >= 0 AND grade < 60 THEN 1 ELSE 0 END) / COUNT(*) AS rateD,\n" +
                "SUM(CASE WHEN grade >= 60 AND grade < 70 THEN 1 ELSE 0 END) / COUNT(*) AS rateC,\n" +
                "SUM(CASE WHEN grade >= 70 AND grade < 85 THEN 1 ELSE 0 END) / COUNT(*) AS rateB,\n" +
                "SUM(CASE WHEN grade >= 85 AND grade <= 100 THEN 1 ELSE 0 END) / COUNT(*) AS rateA " +
                "FROM coursegrade GROUP BY coursename";

        List<GradeAreaInfo> reports = jdbcTemplate.query(sql, (rs, rowNum) -> {
            GradeAreaInfo report = new GradeAreaInfo();
            report.setCoursename(rs.getString("coursename"));
            report.setRateA(rs.getString("rateA"));
            report.setRateB(rs.getString("rateB"));
            report.setRateC(rs.getString("rateC"));
            report.setRateD(rs.getString("rateD"));
            return report;
        });
        return reports;
    }
    public List<gradeInfo> getGradeRanking(String type) {
        List<gradeInfo> gradeInfos = new ArrayList<>();
        if("id".equals(type)){
            String sql = "select stuid, name, sum(grade) as totalgrade from coursegrade " +
                    "group by stuid, name";
            gradeInfos = jdbcTemplate.query(sql, (rs, rowNum) -> {
                gradeInfo grade = new gradeInfo();
                grade.setStuid(rs.getString("stuid"));
                grade.setName(rs.getString("name"));
                grade.setGrade(rs.getInt("totalgrade"));
                return grade;
            });
        }else if("total".equals(type)){
            String sql = "select stuid, name, sum(grade) as totalgrade from coursegrade " +
                    "group by stuid, name order by totalgrade desc";
            gradeInfos = jdbcTemplate.query(sql, (rs, rowNum) -> {
                gradeInfo grade = new gradeInfo();
                grade.setStuid(rs.getString("stuid"));
                grade.setName(rs.getString("name"));
                grade.setGrade(rs.getInt("totalgrade"));
                return grade;
            });
        }else{
            String sql = "select stuid, name, grade from coursegrade where coursename = ? " +
                    "order by grade desc";
            gradeInfos = jdbcTemplate.query(sql, new Object[]{type}, (rs, rowNum) -> {
                gradeInfo grade = new gradeInfo();
                grade.setStuid(rs.getString("stuid"));
                grade.setName(rs.getString("name"));
                grade.setCoursename(type);
                grade.setGrade(rs.getInt("grade"));
                return grade;
            });
        }
        return gradeInfos;
    }
}
