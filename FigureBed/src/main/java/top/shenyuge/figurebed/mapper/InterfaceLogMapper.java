package top.shenyuge.figurebed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.shenyuge.figurebed.bean.InterfaceLog;

/**
 * @author 制冷
 * @date 2024/4/7 下午12:03
 * @description interfaceLogMapper
 */
@Mapper
public interface InterfaceLogMapper extends BaseMapper<InterfaceLog> {

    @Select("CALL init()")
    void initDataBase();
}
