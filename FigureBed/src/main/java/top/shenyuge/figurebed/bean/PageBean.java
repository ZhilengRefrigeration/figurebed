package top.shenyuge.figurebed.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 制冷
 * @date 2024/4/3 21:01
 * @description PageBean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private String pageNum;
    private String pageSize;
    private String search;
}
