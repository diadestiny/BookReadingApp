package com.example.bottomtabber.Util;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<File> getSuffixFile(ArrayList<File> files, String filePath, String suffere){
        File f = new File(filePath);
        if(!f.exists()){
            return null;
        }
        File[] subFiles = f.listFiles();
        Log.d("lkh",filePath);
        if(subFiles == null){
            return null;
        }
        if(files.size()>100){
            return files;
        }
        for(File subFile:subFiles){
            if(subFile.isFile() && subFile.getName().endsWith(suffere)){
                files.add(subFile);
            }else if(subFile.isDirectory()){
                getSuffixFile(files, subFile.getAbsolutePath(), suffere);
            }else{
            }
        }
        return files;
    }
}
