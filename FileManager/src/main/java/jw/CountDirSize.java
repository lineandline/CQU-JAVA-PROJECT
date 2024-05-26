package jw;


import java.io.File;

public class CountDirSize {
    static long countSize = 0;
    static void countDirSize(String path) {
        File pathFile = new File(path);
        String[] list = pathFile.list();
        if (pathFile.isDirectory()) {
            for (String items : list) {
                String subItem=path+File.separator+items;
                //递归调用.
                countDirSize(subItem);
            }
        } else {
            countSize += pathFile.length();//返回文件字节数(无法直接正确作用于文件夹)
        }
    }

    public static long getCountSize(String path) {
        countDirSize(path);
        return countSize;
    }
}


