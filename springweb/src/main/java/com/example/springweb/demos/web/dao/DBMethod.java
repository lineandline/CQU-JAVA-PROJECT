package com.example.springweb.demos.web.dao;

import com.example.springweb.demos.web.cache.InfoCache;
import com.example.springweb.demos.web.entity.gradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBMethod {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean loginConfirm(String username,String password,String role){
        String sql = "SELECT COUNT(*) FROM user WHERE username = ? AND password = ? AND role = ?";
        //0:teacher  1:student  2:admin
        int count=role.equals("teacher")?0:role.equals("student")?1:2;
        count = jdbcTemplate.queryForObject(sql, Integer.class, username, password,count);
        return (count==1);
    }
    public void getStuGrade(String stuId){
        String sql = "SELECT * FROM coursegrade WHERE stuid = ?";
        InfoCache.stuGrade = jdbcTemplate.query(
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
    }
}
