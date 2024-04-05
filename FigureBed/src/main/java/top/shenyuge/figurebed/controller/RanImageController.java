package top.shenyuge.figurebed.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.shenyuge.figurebed.bean.ImgBean;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.service.RanImageService;
import top.shenyuge.figurebed.util.FileUtils;
import top.shenyuge.figurebed.util.MyCacheInit;
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

    private final MyCacheInit myCacheInit;

    private final RanImageService service;

    /**
     * 批量上传文件接口
     * @param multipleFile 批量文件
     * @return 路径
     */

    @PostMapping("/massUpload")
    public Result<Map<String, String>> massUpload(@RequestParam("fileName") List<MultipartFile> multipleFile) {
        if (null == multipleFile){
            return Result.error(500,"没有上传文件");
        }
        HashMap<String, String> uploadIsOk = new HashMap<>();
        multipleFile.forEach((file)->{
            if("".equals(file.getOriginalFilename()) && ImgCache.ranImgAllName.add(file.getOriginalFilename()) && FileUtils.upload(file, path, file.getOriginalFilename())){
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
        String fileName = ImgCache.ranImgAllName.stream().toList().get(new Random().nextInt(ImgCache.ranImgAllName.size()));
        try {
            return Result.requestFileSuccessful(FileUtils.getFile(path, fileName));
        } catch (FileNotFoundException e) {
            return Result.requestNoFile();
        } catch (Exception e) {
            return Result.requestServerError();
        }
    }

    @PostMapping("/getImg")
    public Result<Set<String>> getImg(@RequestBody PageBean pageBean){
        if(null == pageBean || !pageBean.isValidPage()){
            return Result.error(500, "参数错误");
        }
        return Result.success(service.getImg(pageBean));
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
    @PostMapping("delImg")
    public Result<String> delImg(@RequestBody List<ImgBean> imgBeans){
        imgBeans.forEach(imgBean -> {FileUtils.delFile( path+imgBean.getName());ImgCache.ranImgAllName.remove(imgBean.getName());});
        return Result.success("删除成功");
    }

    @Autowired

    public RanImageController(MyCacheInit myCacheInit, RanImageService service) {
        this.myCacheInit = myCacheInit;
        this.service = service;
    }
}
