package jw;

import org.apache.commons.io.IOUtils;

import java.io.*;

public class FileCopy {
    static long srcArea,destArea,startTime,curTime;
    static void copyFile(String src, String dest){
        File srcFile = new File(src);
        File destFile = new File(dest, srcFile.getName());
        if(srcArea==0L){
            startTime = System.currentTimeMillis();
            srcArea = CountDirSize.getCountSize(srcFile.getAbsolutePath());
        }
        if(srcFile.isDirectory()){
            if(!destFile.exists()){
                destFile.mkdirs();
            }
            File[] files = srcFile.listFiles();
            for(File f:files){
                if(f.isDirectory()) {
                    copyFile(f.getAbsolutePath(),destFile.getAbsolutePath()+File.separator+f.getName());
                } else {
                    destArea+=f.length();
                    copyTo(f,new File(destFile.getAbsolutePath()+File.separator+f.getName()));
                    double completionRate = destArea * 1.0 / srcArea;
                    curTime = System.currentTimeMillis()-startTime;
                    System.out.printf("%-20s\t 已运行时间: %d 毫秒\t 当前复制完成比: %.2f%%\n", f.getName(),curTime, 100 * completionRate);
                    if(completionRate>=1){
                        System.out.println("文件夹复制完成");
                        break;
                    }
                }
            }
        }else{
            if(!destFile.exists()){
                destFile.mkdirs();
            }
            copyTo(srcFile,new File(dest+File.separator+srcFile.getName()));
        }

    }
    public static void copyTo(File src, File dest){
        try(InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest, true)){
            IOUtils.copy(in, out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
