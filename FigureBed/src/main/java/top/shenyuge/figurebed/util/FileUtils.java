package top.shenyuge.figurebed.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author 制冷
 * @date 2024/3/25 13:43
 * @description FileUtils
 */
public class FileUtils {


    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName){

        //使用原文件名
        String realPath = path + "/" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        try {
            //保存文件
            file.transferTo(dest);
            return true;
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }
    public static InputStreamResource getFile(String path, String fileName) throws FileNotFoundException {
            File file = new File(path + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            return new InputStreamResource(inputStream);
    }
}
