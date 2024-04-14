package top.shenyuge.figurebed.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author 制冷
 * @date 2024/4/7 上午11:59
 * @description interfaceLog
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("interfaceLog")
public class InterfaceLog {

    @TableId(type = IdType.ASSIGN_UUID)
    private Integer logId;

    private String interfaceName;

    private LocalDate interfaceDate;

    private Integer interfaceCount;

    public InterfaceLog(String interfaceName, LocalDate interfaceDate, Integer interfaceCount) {
        this.interfaceName = interfaceName;
        this.interfaceDate = interfaceDate;
        this.interfaceCount = interfaceCount;
    }
}
