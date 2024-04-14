package top.shenyuge.figurebed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.shenyuge.figurebed.bean.InterfaceLog;

/**
 * @author 制冷
 * @date 2024/4/7 下午12:04
 * @description interfaceLogService
 */
public interface InterfaceLogService extends IService<InterfaceLog> {
void initDataBase();
}
