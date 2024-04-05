package top.shenyuge.figurebed.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author 制冷
 * @date 2024/3/25 13:43
 * @description FileUtils
 */
@Slf4j
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
            return false;
        }

    }

    /**
     * 读取文件
     * @param path 路径
     * @param fileName 文件名
     * @return 文件
     * @throws FileNotFoundException
     */
    public static InputStreamResource getFile(String path, String fileName) throws FileNotFoundException {
            File file = new File(path + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            return new InputStreamResource(inputStream);
    }

    /**
     * 删除指定文件
     * @param path 要删除文件的路径
     * @return 是否删除成功
     */

    public static boolean delFile(String path){
        try {
            Files.delete(Path.of(path));
            log.info("删除文件:{}", path);
            return true;
        } catch (IOException e) {
            log.error("删除文件错误：{}", e.getMessage());
            return false;
        }
    }
}
