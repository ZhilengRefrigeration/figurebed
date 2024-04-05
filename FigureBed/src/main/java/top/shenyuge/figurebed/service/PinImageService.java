package top.shenyuge.figurebed.service;

import org.springframework.core.io.InputStreamResource;
import top.shenyuge.figurebed.bean.PageBean;

import java.util.List;

/**
 * @author 制冷
 * @date 2024/4/3 21:11
 * @description PinImageService
 */
public interface PinImageService {

    List<InputStreamResource> getImg(PageBean page);

}
