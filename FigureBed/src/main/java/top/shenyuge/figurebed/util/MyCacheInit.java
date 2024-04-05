package top.shenyuge.figurebed.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import top.shenyuge.figurebed.cache.ImgCache;

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

    @PostConstruct
    public void runRan() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + RanPath + "/*.{jpg,jpeg,png,gif}");
        for (Resource resource : resources) {
            ImgCache.ranImgAllName.add(resource.getFilename());
        }
    }
    @PostConstruct
    public void runPin() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("file:" + PinPath + "/*.{jpg,jpeg,png,gif}");
        for (Resource resource : resources) {
            ImgCache.pinImgAllName.add(resource.getFilename());
        }
    }
}
