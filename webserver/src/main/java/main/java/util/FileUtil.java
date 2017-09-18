package main.java.util;

import java.io.File;

/**
 * Created by root on 9/11/17.
 */
public class FileUtil {
    /**
     * Delete file or directory by name
     * @param file
     * @return
     */
    public static boolean deleteFile(String file){
        return deleteFile(new File(file));
    }

    /**
     * Delete file or directory by File object
     * @param file
     * @return
     */
    public static boolean deleteFile(File file){
        if(!file.exists()) return true;
        if(file.isDirectory()){
            for(String child:file.list()){
                if(!deleteFile(new File(file,child)))
                    return false;
            }
        }
        return file.delete();
    }
    public static void main(String[] args){
        deleteFile("/tmp/LogAnalysisSystem/NDDOutput");
    }
}
