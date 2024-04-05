package top.shenyuge.figurebed.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author 制冷
 * @date 2024/4/5 下午6:18
 * @description ImgBean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgBean {

    //文件名
    private String name;

    //上传时间
    private LocalDateTime uploadTime;

    //大小(Mb)
    private Double size;
}
