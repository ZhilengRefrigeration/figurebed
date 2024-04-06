package top.shenyuge.figurebed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.shenyuge.figurebed.bean.ImgBean;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.service.PinImageService;
import top.shenyuge.figurebed.util.FileUtils;
import top.shenyuge.figurebed.util.Result;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author 制冷
 * @date 2024/3/25 13:27
 * @description PinImage
 */
@RestController
@RequestMapping("/pin")
public class PinImageController {

    @Value("${server.public-address}/pin")
    private String url;


    @Value("${web.upload-path-pin}")
    private String path;

    private final PinImageService service;


    /**
     * 批量上传文件接口
     *
     * @param multipleFile 批量文件
     * @return 路径
     */

    @PostMapping("/massUpload")
    public Result<Map<String, String>> massUpload(@RequestParam("fileName") List<MultipartFile> multipleFile) {
        if (null == multipleFile) {
            return Result.error(500, "没有上传文件");
        }
        HashMap<String, String> uploadIsOk = new HashMap<>();
        multipleFile.forEach((file) -> {
            if (!"".equals(file.getOriginalFilename()) && ImgCache.pinImgAllName.add(file.getOriginalFilename()) && FileUtils.upload(file, path, file.getOriginalFilename())) {
                uploadIsOk.put(file.getOriginalFilename(), url + "/show?fileName=" + file.getOriginalFilename());
            } else {
                uploadIsOk.put(file.getOriginalFilename(), "上传失败");
            }
        });
        return Result.success(uploadIsOk);
    }

    /**
     * 获取单个文件
     * fileName 文件名
     * @return 图片
     */
    @GetMapping("/show")
    public ResponseEntity<InputStreamResource> showPhotos(String fileName) {
        try {
            if (fileName.endsWith("png") || fileName.endsWith("jpg") || fileName.endsWith("gif")) {
                return Result.requestFileSuccessful(FileUtils.getFile(path, fileName));
            } else {
                return Result.requestFileSuccessfulIsFile(FileUtils.getFile(path, fileName), fileName);
            }
        } catch (FileNotFoundException e) {
            return Result.requestNoFile();
        } catch (Exception e) {
            return Result.requestServerError();
        }
    }

    /**
     * 分页查询图克
     *
     * @param page 分页数据
     * @return 图片地址
     */
    @PostMapping("/getImg")
    public Result<Set<String>> getImg(@RequestBody PageBean page) {
        if (null == page || !page.isValidPage()) {
            return Result.error(500, "参数错误");
        }
        return Result.success(String.valueOf(ImgCache.pinImgAllName.size()),service.getImg(page));
    }

    @PostMapping("delImg")
    public Result<String> delImg(@RequestBody List<ImgBean> imgBeans) {
        imgBeans.forEach(imgBean -> {
            FileUtils.delFile(path + imgBean.getName());
            ImgCache.pinImgAllName.remove(imgBean.getName());
        });
        return Result.success("删除成功");
    }

    @Autowired
    public PinImageController(PinImageService service) {
        this.service = service;
    }
}
