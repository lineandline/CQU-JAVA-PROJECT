package jw;

import org.apache.commons.io.FileUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileMethod {
    private static final String ALGORITHM = "AES";
    static File copyFile = new File(System.getProperty("user.dir"));
    public void mkdir(String CurrentFile,String name){
        String path = CurrentFile + File.separator +name;
        boolean mkdirOk = new File(path).mkdir();
        if(mkdirOk){
            System.out.println("创建成功");
        }else{
            System.out.println("创建失败");
        }
    }
    public void mkFile(String CurrentFile,String name){
        String path = CurrentFile + File.separator +name;
        boolean mkFileOk = false;
        try {
            mkFileOk = new File(path).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(mkFileOk){
            System.out.println("创建成功");
        }else{
            System.out.println("创建失败");
        }
    }
    public void delete(String CurrentFile,String name){
        String path = CurrentFile + File.separator +name;
        boolean deleteOk;
        File file = new File(path);
        deleteOk = FileUtils.deleteQuietly(file);
        if(deleteOk){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }
    public void open(String CurrentFile,String name){
        String path = CurrentFile + File.separator +name;
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(path);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.out.println("打开文件时发生错误");
                e.printStackTrace();
            }
        } else {
            System.out.println("当前环境不支持");
        }
    }
    public void show(String CurrentFile,String[] arg){
        String path = CurrentFile + File.separator +arg[1];
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            if(arg.length==4){
                if (arg[3].equals("name")) {
                    // 按名称排序
                    Arrays.sort(files, Comparator.comparing(File::getName));
                } else if (arg[3].equals("time")){
                    // 按最近修改时间排序
                    Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                } else if (arg[3].equals("byte")) {
                    // 按文件大小排序
                    Arrays.sort(files, Comparator.comparingLong(File::length));
                } else {
                    System.out.println("输入错误");
                }
            }else if(arg.length==2){
                Arrays.sort(files, Comparator.comparing(File::getName));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (File file : files) {
                String fileName = file.getName();
                String lastModified = sdf.format(new Date(file.lastModified()));
                String type = file.isDirectory() ? "目录" : "文件";
                long size = file.length();
                System.out.printf("%-24s\t%-20s\t%-8s\t%d Bytes\n", fileName, lastModified, type, size);
            }
        } else {
            System.out.println("目录不存在或不是一个目录");
        }
    }
    public void copy(String CurrentFile,String name){
        String path = CurrentFile + File.separator +name;
        copyFile = new File(path);
        System.out.println("路径已复制");
    }
    public void paste(String CurrentFile,String[] arg){
        String path = CurrentFile + File.separator +arg[1];
        System.out.println(copyFile.getAbsolutePath());
        File absoluteFile = new File(arg[1]);//相对地址
        File relativeFile = new File(path);//绝对地址
        File resultFile = new File(System.getProperty("user.dir"));
        Boolean flag = false;
        if(relativeFile.exists()) {
            resultFile = relativeFile;
            flag = true;
        } else if(absoluteFile.exists()){
            resultFile = absoluteFile;
            flag = true;
        } else {
            System.out.println("文件夹路径有误");
        }
        if(arg.length==2&&flag){
            FileCopy.copyFile(copyFile.getAbsolutePath(),resultFile.getAbsolutePath());
        } else if(arg.length==4&&arg[3].equals("front")&&flag){//前台执行
            FileCopy.copyFile(copyFile.getAbsolutePath(),resultFile.getAbsolutePath());
        } else if(arg.length==4&&arg[3].equals("back")&&flag){//后台执行，启动新建线程
            back(copyFile,resultFile);
        }
    }
    public void back(File copyFile,File finalResultFile){
        Thread CopyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(copyFile.isFile()){//粘贴文件
                    try {
                        FileUtils.copyFileToDirectory(copyFile,finalResultFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(copyFile.isDirectory()){//粘贴文件夹
                    try {
                        FileUtils.copyDirectory(copyFile,finalResultFile);
                        System.out.println("\n复制粘贴成功");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        CopyThread.start();//进程启动
    }

    public void encrypt(String CurrentFile,String[] arg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String srcFile = CurrentFile + File.separator +arg[1];
        String destFile = CurrentFile + File.separator +arg[3];
        System.out.println("请输入加密密匙（1到16位）");
        Scanner scanner=new Scanner(System.in);
        String secretKey=scanner.nextLine();
        int t =0;
        while(secretKey.length()<16){
            secretKey+=secretKey.charAt(t);
            t++;
        }
        // 使用密钥字符串生成秘密密钥
        SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        // 获取 AES 加密算法的实例
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 使用秘密密钥初始化密码 cipher，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 源文件路径
        Path sourcePath = Paths.get(srcFile);
        // 加密文件路径
        Path destinationPath = Paths.get(destFile);
        // 创建输入流，读取源文件
        try (InputStream inputStream = Files.newInputStream(sourcePath);
             // 创建输出流，写入加密文件
             OutputStream outputStream = Files.newOutputStream(destinationPath);
             // 创建密码输出流，连接到输出流，并使用密码 cipher 进行加密
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            // 读取源文件内容到缓冲区
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                //写入加密文件
                cipherOutputStream.write(buffer, 0, bytesRead);
            }

        }
//        Files.delete(sourcePath); // 删除原文件
    }

    public void decrypt(String CurrentFile,String[] arg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        String srcFile = CurrentFile + File.separator +arg[1];
        String destFile = CurrentFile + File.separator +arg[3];
        System.out.println("请输入解密密匙");
        Scanner scanner=new Scanner(System.in);
        String secretKey=scanner.nextLine();
        int t =0;
        while(secretKey.length()<16){
            secretKey+=secretKey.charAt(t);
            t++;
        }
        SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        Path sourcePath = Paths.get(srcFile);
        Path destinationPath = Paths.get(destFile);

        try (InputStream inputStream = Files.newInputStream(sourcePath);
             OutputStream outputStream = Files.newOutputStream(destinationPath);
             CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                System.out.println(bytesRead);
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
    public void zip(String CurrentFile,String name){
        String srcFile = CurrentFile + File.separator +name,destFile = "";
        File srcFileTemp = new File(srcFile);
        if(srcFileTemp.isDirectory()){
            destFile = CurrentFile + File.separator +name+".zip";
        }else if(srcFileTemp.isFile()){
            destFile = CurrentFile + File.separator +name.substring(0,name.lastIndexOf('.'))+".zip";
        }

        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(destFile))){
            fileZip(srcFileTemp,srcFileTemp.getName(),zipOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void fileZip(File srcFile,String fileName,ZipOutputStream zipOut) throws IOException {
        if(srcFile.isHidden()){
            return;
        }
        if(srcFile.isDirectory()){
            File[] fileList = srcFile.listFiles();
            for(File f:fileList){
                fileZip(f,fileName+File.separator+f.getName(),zipOut);
            }
        }else{
            byte[] buffer = new byte[4096];
            FileInputStream fis = new FileInputStream(srcFile);
            zipOut.putNextEntry(new ZipEntry(fileName));
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zipOut.write(buffer, 0, length);
            }
            fis.close();
        }
    }
    public void unzip(String CurrentFile,String name) throws IOException {
        String srcFile = CurrentFile + File.separator +name;
        String destFile = CurrentFile;
        File destDir = new File(CurrentFile + File.separator +name.substring(0,name.lastIndexOf('.')));
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(srcFile))){
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                String fileTemp = destFile + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    byte[] buffer = new byte[4096];
                    FileOutputStream fos = new FileOutputStream(fileTemp);
                    int length;
                    while ((length = zipIn.read(buffer)) >= 0) {
                        fos.write(buffer, 0, length);
                    }
                    fos.close();
                } else {
                    File dir = new File(fileTemp);
                    dir.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Path sourceFile = Paths.get(srcFile);
        Files.delete(sourceFile);
    }

}
