package top.shenyuge.figurebed.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author 制冷
 * @date 2024/4/3 21:01
 * @description PageBean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {
    private Integer pageNum;
    private Integer pageSize;
    private String search;

    public boolean isValidPage() {
        return Optional.ofNullable(pageNum)
                .filter(pageNum -> pageNum > 0)
                .isPresent() &&
                Optional.ofNullable(pageSize)
                        .filter(pageSize -> pageSize > 0)
                        .isPresent();
    }
}
