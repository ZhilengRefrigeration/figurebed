package top.shenyuge.figurebed.service;

import top.shenyuge.figurebed.bean.PageBean;
import java.util.Set;

/**
 * @author 制冷
 * @date 2024/4/5 下午5:02
 * @description RanImageService
 */
public interface RanImageService {

    Set<String> getImg(PageBean pageBean);
}
