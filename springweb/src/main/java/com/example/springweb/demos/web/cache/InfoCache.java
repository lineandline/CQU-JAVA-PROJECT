package com.example.springweb.demos.web.cache;

import com.example.springweb.demos.web.entity.gradeInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class InfoCache {
    public static List<gradeInfo> stuGrade = new ArrayList<gradeInfo>();
    public static String username;
}
