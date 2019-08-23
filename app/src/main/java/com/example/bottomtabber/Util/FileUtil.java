package com.example.bottomtabber.Util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    public static List<File> getSuffixFile(ArrayList<File> files, String filePath, String suffere){
        File f = new File(filePath);
        if(!f.exists()){
            return null;
        }
        File[] subFiles = f.listFiles();
        if(files.size()>50){
            return files;
        }
        if(subFiles == null){
            return null;
        }
        for(File subFile:subFiles){
            if(subFile.isFile() && subFile.getName().endsWith(suffere) && subFile.length()/1024<1000 &&  subFile.length()/1024>2){
                files.add(subFile);
                Log.d("lkh",subFile.length()/1024+"kb");
            }else if(subFile.isDirectory()){
                getSuffixFile(files, subFile.getAbsolutePath(), suffere);
            }else{

            }
        }
        return files;
    }
    private static String getCharset(String filePath) {
        BufferedInputStream bis = null;
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(filePath));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.mark(0);
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return charset;
    }

    public static String getFileContent1(File file){
        int lineCount = 0 ;
        String content = "";
        try {
            FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
            String charset = FileUtil.getCharset(file.getAbsolutePath());
            Scanner sc = new Scanner(inputStream,charset);
            while (sc.hasNextLine() && lineCount<200){
                String line = sc.nextLine();
                content= content+line+"\n";
                lineCount++;
            }
            content = content.replaceAll("\\n","\n");
            if(sc.ioException() != null) {
                throw sc.ioException();
            }
                inputStream.close();
                sc.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
            return content;
    }
    public static String getFileContent2(File file){
        String content = "";
        try {
            FileInputStream inputStream = new FileInputStream(file.getAbsoluteFile());
            String charset = FileUtil.getCharset(file.getAbsolutePath());
            Scanner sc = new Scanner(inputStream,charset);
            int i=0;
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                content= content+line+"\n";
                i++;
                Log.d("lkh",i+"次数");
            }
            content = content.replaceAll("\\n","\n");
            if(sc.ioException() != null) {
                throw sc.ioException();
            }
            inputStream.close();
            sc.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

}
