package top.shenyuge.figurebed.service;

import top.shenyuge.figurebed.bean.PageBean;
import java.util.Set;

/**
 * @author 制冷
 * @date 2024/4/3 21:11
 * @description PinImageService
 */
public interface PinImageService {

    Set<String> getImg(PageBean page);

}
