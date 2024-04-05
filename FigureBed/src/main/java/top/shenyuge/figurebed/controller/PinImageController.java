package top.shenyuge.figurebed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.util.FileNameUtils;
import top.shenyuge.figurebed.util.FileUtils;
import top.shenyuge.figurebed.util.MyCacheInit;
import top.shenyuge.figurebed.util.Result;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 制冷
 * @date 2024/3/25 13:27
 * @description PinImage
 */
@RestController
@RequestMapping("/pin")
public class PinImageController {

    @Value("${server.public-address}/pin")
    private  String url;


    @Value("${web.upload-path-pin}")
    private String path;

    private MyCacheInit myCacheInit;




    /**
     * 批量上传文件接口
     * @param multipleFile 批量文件
     * @return 路径
     */

    @PostMapping("/massUpload")
    public Result<Map<String, String>> massUpload(@RequestParam("fileName") List<MultipartFile> multipleFile) throws Exception {
        HashMap<String, String > uploadIsOk = new HashMap<>();
        multipleFile.forEach((file)->{
            if(FileUtils.upload(file, path, file.getOriginalFilename())){
                uploadIsOk.put(file.getOriginalFilename(), url+"/show?fileName="+file.getOriginalFilename());
            }else{
                uploadIsOk.put(file.getOriginalFilename(), "上传失败");
            }
        });
        myCacheInit.runPin();
        return Result.success(uploadIsOk);
    }

    /**
     * 获取单张图片
     * @return 图片
     */
    @GetMapping("/show")
    public ResponseEntity<InputStreamResource>  showPhotos(String fileName){
        try {
            return Result.requestFileSuccessful(FileUtils.getFile(path, fileName));
        } catch (FileNotFoundException e) {
            return Result.requestNoFile();
        } catch (Exception e) {
            return Result.requestServerError();
        }
    }

    /**
     * 分页查询图克
     * @param page 分页数据
     * @return 图片
     */
//    @PostMapping("/getImg")
//    public ResponseEntity<List<InputStreamResource>> getImg(PageBean page){
//
//    }

    @Autowired
    public PinImageController(MyCacheInit myCacheInit) {
        this.myCacheInit = myCacheInit;
    }
}
