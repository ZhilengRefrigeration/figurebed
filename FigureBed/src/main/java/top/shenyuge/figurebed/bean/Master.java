package top.shenyuge.figurebed.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * @author 制冷
 * @date 2024/3/28 21:20
 * @description Master
 */
@Data
@TableName("master")
@AllArgsConstructor
@NoArgsConstructor
public class Master {

    @TableId(type = ASSIGN_ID)
    private String id;

    private String userName;

    private String passWord;
}
