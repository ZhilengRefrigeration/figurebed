package top.shenyuge.figurebed.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.util.FileUtils;
import top.shenyuge.figurebed.util.Result;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author 制冷
 * @date 2024/3/25 14:17
 * @description RanImgageController
 */
@Slf4j
@RestController
@RequestMapping("/ran")
public class RanImageController {
    @Value("${web.upload-path-ran}")
    private String path;

    @Value("${server.public-address}/ran")
    private  String url;

    /**
     * 批量上传文件接口
     * @param multipleFile 批量文件
     * @return 路径
     */

    @PostMapping("/massUpload")
    public Result<Map<String, String>> massUpload(@RequestParam("fileName") List<MultipartFile> multipleFile){
        HashMap<String, String> uploadIsOk = new HashMap<>();
        multipleFile.forEach((file)->{
            if(ImgCache.ranImgAllName.stream().anyMatch(fileName->fileName.equals(file.getOriginalFilename()))){
                uploadIsOk.put(file.getOriginalFilename(), "图片名重复!");
            }else if(FileUtils.upload(file, path, file.getOriginalFilename())){
                uploadIsOk.put(file.getOriginalFilename(), url+"/show?fileName="+file.getOriginalFilename());
            }else{
                uploadIsOk.put(file.getOriginalFilename(), "上传失败");
            }
        });
        return Result.success(uploadIsOk);
    }

    /**
     * 获取随机图片
     * @return 随机一张图片
     */
    @GetMapping("/randomShow")
    public ResponseEntity<InputStreamResource> randomShow() {
        if (ImgCache.ranImgAllName.isEmpty()) {
            return Result.requestServerError();
        }
        String fileName = ImgCache.ranImgAllName.get(new Random().nextInt(ImgCache.ranImgAllName.size()));
        try {
            return Result.requestFileSuccessful(FileUtils.getFile(path, fileName));
        } catch (FileNotFoundException e) {
            return Result.requestNoFile();
        } catch (Exception e) {
            return Result.requestServerError();
        }
    }

    /**
     * 获取单张图片
     * @return 图片
     */
    @GetMapping("show")
    public ResponseEntity<InputStreamResource>  showPhotos(String fileName){
        try {
            return Result.requestFileSuccessful(FileUtils.getFile(path, fileName));
        } catch (FileNotFoundException e) {
            return Result.requestNoFile();
        } catch (Exception e) {
            return Result.requestServerError();
        }
    }
}
