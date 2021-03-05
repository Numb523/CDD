package com.example.test.EntityClass;
import android.os.Environment;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Register{
    public Register(){
        nickNameLibrary=new NickNameLibrary();
        pictureLibrary=new PictureLibrary();
        //file=new File("/main/java/game/cddgameapp/src/TextFile/test1.txt");//这个目录可以改
        file=new File(Environment.getExternalStorageDirectory().getPath() + "/testTXT"+"/test1.txt");//这个目录可以改
    }
    static {
        nickNameLibrary=new NickNameLibrary();
        pictureLibrary=new PictureLibrary();
        //file=new File("/main/java/game/cddgameapp/src/TextFile/test1.txt");//这个目录可以改
        file=new File(Environment.getExternalStorageDirectory().getPath() + "/testTXT"+"/test1.txt");//这个目录可以改
    }
    public static NickNameLibrary nickNameLibrary;
    public static PictureLibrary pictureLibrary;
    public static File file;//存储信息的文件

    public static void givingNickName(String nickName){
        nickNameLibrary.givingNickName(nickName);
    }
    public static void givingPicutre(int number){
        pictureLibrary.givingPicutre(number);
    }

    public String selectNickName(int number){
        return nickNameLibrary.selectNickName(number);
    }
    public int selectHead(int number){
        return pictureLibrary.selectPicture(number);
    }

    //新增
    public static ArrayList<String> getNickNameList() { return nickNameLibrary.nickNameList; }

    public static int login(String nickName){//根据昵称获取对应的头像编号0-8，如果列表中不存在传入的昵称或者列表长度为0，返回9
        updateInfo();
        int tempSize=nickNameLibrary.nickNameList.size();
        if(tempSize==0)
            return 9;
        for(int i=0;i<tempSize;i++){
            if(nickNameLibrary.nickNameList.get(i).equals(nickName))
                return pictureLibrary.pictureList.get(i);
        }
        return 9;
    }
    public static boolean regist(String nickName,int pictureNumber){
        return recordInfo(nickName,pictureNumber);
    }
    //向文件写入昵称和对应的头像编号
    public static boolean recordInfo(String nickName,int pictureNumber){
        updateInfo();
        int tempSize=nickNameLibrary.nickNameList.size();
        if(tempSize!=0){
            for(int i=0;i<tempSize;i++){
                if(nickNameLibrary.nickNameList.get(i).equals(nickName))
                    return false;//如果已经存在于列表中就不需要再写入了
            }
        }

        if(!nickName.contains("\\|")&&pictureNumber>=0&&pictureNumber<=8){
            String temp=new String(nickName+"|"+pictureNumber);
            saveDataToFile(temp);
            return true;
        }
        else
            return false;//传入的昵称不符合要求，或是编号超出范围
    }
    //读取文件，更新已有的昵称列表和头像列表
    public static void updateInfo(){
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(readFile().split(" ")));
        int number=0;
        ArrayList<String> temp;
        for(String s:arrayList){
            temp=new ArrayList<String>(Arrays.asList(s.split("\\|")));
            if(temp.size()>1) {
                number = Integer.parseInt(temp.get(1));
                if (!nickNameLibrary.nickNameList.contains(temp.get(0)) && number >= 0 && number <= 8) {
                    givingNickName(temp.get(0));
                    givingPicutre(number);
                }
                temp.clear();
            }
        }
    }
    private static void saveDataToFile(String data){//存储昵称到文件中
        BufferedWriter writer = null;
        /*
        String destDirName="src/TextFile";
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
        }
        System.out.println(file+"file");

         */
        //如果文件不存在，则新建一个
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println( "文件不存在");
        }
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8"));
            writer.write(data+" ");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
    }
    private static String readFile() {//读取文件

        //为了确保文件一定在之前是存在的，将字符串路径封装成File对象
        //File file = new File("src/TextFile/" + fileName+ ".txt");
        if(!file.exists()){
            //throw new RuntimeException("要读取的文件不存在");
            return " ";
        }
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null){
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("文件读取成功");
        return laststr;
    }
    public static void clearInfoForFile() {//清空文件中的信息,慎用
        //File file = new File("src/TextFile/" + fileName+ ".txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //打印函数方便检查，可以改
    public String toString(){
        String temp=new String();
        for(String s:nickNameLibrary.nickNameList){
            temp+=s;
        }
        for(int i:pictureLibrary.pictureList){
            temp+=i;
        }
        return temp;
    }
}