package jw;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    static File CurrentFile = new File(System.getProperty("user.dir"));
    static File HistoryFile = new File(System.getProperty("user.dir"));
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        Scanner scanner = new Scanner(System.in);
        FileMethod fileMethod = new FileMethod();
        System.out.println("基于命令行的文件管理系统");
        System.out.println(  "mkdir:新建文件夹(mkdir name)      mkFile:新建文件(mkFile name)                        delete:删除文件夹或文件(delete name)\n"
                            +"open:打开文件(open name)          show:文件夹内容罗列(show name by 'name/time/byte')  cd:路径跳转(cd 'path')\n"
                            +"copy:复制文件或文件夹(copy name)   paste:粘贴文件夹(paste folder in front/back)       encrypt:文件或文件夹加密(encrypt name to newName)\n"
                            +"decrypt:解密(decrypt name to newName)        zip:文件或文件夹压缩(zip name)           unzip:解压缩(unzip name)");
        System.out.println("quit, 退出");
        String command = "begin now!";
        // 执行显示系统主菜单
        String[] arg;
        while(!command.equals("quit")){
            System.out.print(CurrentFile.getAbsolutePath()+": ");
            command = scanner.nextLine();
            if(command.contains("  ")){
                System.out.println("输入命令中不能包含两个空格！");
                break;
            }
            arg = command.split(" ");
            commandDeal(arg,CurrentFile.getAbsolutePath(),fileMethod);
        }
    }
    public static void commandDeal(String[] arg,String file,FileMethod fileMethod) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        if ("mkdir".equals(arg[0])) {
            fileMethod.mkdir(file, arg[1]);
        } else if ("mkFile".equals(arg[0])) {
            fileMethod.mkFile(file, arg[1]);
        } else if ("delete".equals(arg[0])) {
            fileMethod.delete(file, arg[1]);
        } else if ("open".equals(arg[0])) {
            fileMethod.open(file,arg[1]);
        } else if ("show".equals(arg[0])) {
            fileMethod.show(file,arg);
        } else if ("cd".equals(arg[0])){
            changeDirection(arg[1]);
        } else if ("copy".equals(arg[0])){
            fileMethod.copy(file,arg[1]);
        } else if ("paste".equals(arg[0])){
            fileMethod.paste(file,arg);
        } else if ("encrypt".equals(arg[0])){
            fileMethod.encrypt(file,arg);
        } else if ("decrypt".equals(arg[0])){
            fileMethod.decrypt(file,arg);
        } else if ("zip".equals(arg[0])){
            fileMethod.zip(file,arg[1]);
        } else if ("unzip".equals(arg[0])){
            fileMethod.unzip(file,arg[1]);
        } else {
            System.out.println("当前指令未匹配");
        }
    }
    public static void changeDirection(String str){
        File absoluteFile = new File(str);
        File relativeFile = new File(CurrentFile.getAbsolutePath()+File.separator+str);
        if(str.equals("back")){
            CurrentFile = HistoryFile;//历史地址
        }
        else if(str.equals("..")){//上一级
            HistoryFile = CurrentFile;
            CurrentFile = CurrentFile.getParentFile();
        } else if(relativeFile.isDirectory()){//绝对地址
            HistoryFile = CurrentFile;
            CurrentFile = relativeFile;
        } else if(absoluteFile.isDirectory()) {//相对地址
            HistoryFile = CurrentFile;
            CurrentFile = absoluteFile;
        }
    }



}
