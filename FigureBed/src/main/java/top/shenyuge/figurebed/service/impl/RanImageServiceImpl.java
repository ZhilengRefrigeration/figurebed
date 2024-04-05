package top.shenyuge.figurebed.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.cache.ImgCache;
import top.shenyuge.figurebed.service.RanImageService;
import top.shenyuge.figurebed.util.FileNameUtils;
import java.util.Set;

/**
 * @author 制冷
 * @date 2024/4/5 下午5:02
 * @description RanImageServiceImpl
 */
@Service
public class RanImageServiceImpl implements RanImageService {

    @Value("${server.public-address}/ran/show?fileName=")
    private String url;

    @Override
    public Set<String> getImg(PageBean pageBean) {
        return FileNameUtils.searchImg(ImgCache.ranImgAllName, pageBean, url);
    }
}
