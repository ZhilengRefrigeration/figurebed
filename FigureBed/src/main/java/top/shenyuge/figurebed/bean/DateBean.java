package top.shenyuge.figurebed.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 制冷
 * @date 2024/4/7 下午12:41
 * @description DataBean
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateBean {
    private LocalDate startDate;
    private LocalDate endDate;
    private String search;

    public boolean isValidDate(){
        return null != this.endDate && null != this.startDate && this.startDate.isBefore(this.endDate);
    }
}
