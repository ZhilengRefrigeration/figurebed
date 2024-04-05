package top.shenyuge.figurebed.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.service.PinImageService;
import top.shenyuge.figurebed.util.FileNameUtils;
import java.util.Set;

/**
 * @author 制冷
 * @date 2024/4/3 21:15
 * @description PinImageServiceImpl
 */
@Slf4j
@Service
public class PinImageServiceImpl implements PinImageService {

    @Value("${server.public-address}/pin/show?fileName=")
    private  String url;

    @Override
    public Set<String> getImg(PageBean page) {
        return FileNameUtils.searchImg(ImgCache.pinImgAllName, page, url);
    }
}
