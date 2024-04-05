package top.shenyuge.figurebed.service.impl;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import top.shenyuge.figurebed.bean.PageBean;
import top.shenyuge.figurebed.service.PinImageService;

import java.util.List;

/**
 * @author 制冷
 * @date 2024/4/3 21:15
 * @description PinImageServiceImpl
 */
@Service
public class PinImageServiceImpl implements PinImageService {
    @Override
    public List<InputStreamResource> getImg(PageBean page) {
        return null;
    }
}
