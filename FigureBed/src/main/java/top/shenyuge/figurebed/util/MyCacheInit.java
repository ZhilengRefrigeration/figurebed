package top.shenyuge.figurebed.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.shenyuge.figurebed.bean.InterfaceLog;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.service.InterfaceLogService;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

/**
 * @author 制冷
 * @date 2024/3/25 14:13
 * @description MyCacheInit
 */
@Slf4j
@Component
public class MyCacheInit {
    @Value("${web.upload-path-ran}")
    private String RanPath;

    @Value("${web.upload-path-pin}")
    private String PinPath;


    private final InterfaceLogService service;

    @Autowired
    public MyCacheInit(InterfaceLogService service) {
        this.service = service;
    }

    @PostConstruct
    public void runRan() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + RanPath + "/*.{jpg,jpeg,png,gif}");
        ImgCache.ranImgAllName.clear();
        for (Resource resource : resources) {
            ImgCache.ranImgAllName.add(resource.getFilename());
        }

    }
    @PostConstruct
    public void runPin() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + PinPath + "/*.{jpg,jpeg,png,gif}");
        ImgCache.pinImgAllName.clear();
        for (Resource resource : resources) {
            ImgCache.pinImgAllName.add(resource.getFilename());
        }
    }

    @PostConstruct
    public void cleaningRepetition() {
        new Thread(()->{

            log.info("开始清理.....");
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resourcesRan;
            try {
                resourcesRan = resolver.getResources("file:" + RanPath + "/*.{jpg,jpeg,png,gif}");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Set<String> uniqueFileHashes = new HashSet<>();

            for (Resource resource : resourcesRan) {
                try {
                    byte[] fileContent = resource.getContentAsByteArray();
                    if(fileContent.length == 0){
                        System.out.println("清除无效照片: " + resource.getFilename());
                        FileUtils.delFile(RanPath + resource.getFilename());
                        continue;
                    }
                    String fileHash = getFileHash(fileContent);

                    if (!uniqueFileHashes.contains(fileHash)) {
                        uniqueFileHashes.add(fileHash);
                    } else {
                        System.out.println("清除重复照片: " + resource.getFilename());
                        FileUtils.delFile(RanPath + resource.getFilename());
                    }
                } catch (IOException | NoSuchAlgorithmException e) {
                   log.error(e.getMessage());
                }
            }

            try {
                Thread.sleep(1000*60*60*24);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                runPin();
            } catch (Exception e) {
               log.error(e.getMessage());
            }
        }).start();
    }

    private String getFileHash(byte[] fileContent) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md5.digest(fileContent);
        StringBuilder hexHash = new StringBuilder();

        for (byte b : hashBytes) {
            hexHash.append(String.format("%02x", b));
        }

        return hexHash.toString();
    }

    @Scheduled(cron = "30 0 0 * * ?")
    public void sayHello() {
        LambdaQueryWrapper<InterfaceLog> getNowLog = new LambdaQueryWrapper<>();
        getNowLog
                .eq(InterfaceLog::getInterfaceDate, LocalDate.now());
        if(!(service.list(getNowLog).size()>11)){
            service.initDataBase();
        }
    }
}
