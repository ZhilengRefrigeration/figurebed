package top.shenyuge.figurebed.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.shenyuge.figurebed.bean.DateBean;
import top.shenyuge.figurebed.bean.InterfaceLog;
import top.shenyuge.figurebed.service.InterfaceLogService;
import top.shenyuge.figurebed.util.Result;

import java.util.List;

/**
 * @author 制冷
 * @date 2024/4/7 下午12:39
 * @description InterfaceLogController
 */
@RestController
@RequestMapping("/log")
public class InterfaceLogController {
    private final InterfaceLogService service;

    @PostMapping("/getAllLog")
    public Result<List<InterfaceLog>> getAllLog(@RequestBody DateBean dateBean){
        if(!dateBean.isValidDate()){
            return Result.error(500, "");
        }
        LambdaQueryWrapper<InterfaceLog> getLogByDate = new LambdaQueryWrapper<>();
        getLogByDate
                .ge(InterfaceLog::getInterfaceDate, dateBean.getStartDate())
                .le(InterfaceLog::getInterfaceDate, dateBean.getEndDate())
                .eq(StringUtils.isNotBlank(dateBean.getSearch()),InterfaceLog::getInterfaceName, dateBean.getSearch())
                .orderByAsc(InterfaceLog::getInterfaceDate);
        return Result.success(service.list(getLogByDate));
    }

    @Autowired
    public InterfaceLogController(InterfaceLogService service) {
        this.service = service;
    }
}
