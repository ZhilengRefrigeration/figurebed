package top.shenyuge.figurebed.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.shenyuge.figurebed.bean.InterfaceLog;
import top.shenyuge.figurebed.service.InterfaceLogService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * @author 制冷
 * @date 2024/4/7 上午11:29
 * @description AopAdvice
 */
@Aspect
@Component
public class AopAdvice {

    private final InterfaceLogService service;

    @After("execution (* top.shenyuge.figurebed.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        String interfaceName = joinPoint.getTarget().getClass().getName().split("\\.")[4].substring(0,3).toLowerCase()+"-"+joinPoint.getSignature().getName();
        LambdaQueryWrapper<InterfaceLog> getNowLog = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<InterfaceLog> upLog = new LambdaQueryWrapper<>();

        getNowLog
                .eq(InterfaceLog::getInterfaceName, interfaceName)
                .eq(InterfaceLog::getInterfaceDate, LocalDate.now());
        upLog
                .eq(InterfaceLog::getInterfaceName, interfaceName)
                .eq(InterfaceLog::getInterfaceDate, LocalDate.now());

        InterfaceLog one = null;
        try {
            one = service.getOne(getNowLog);
        } catch (TooManyResultsException e) {
            List<InterfaceLog> list = service.list(getNowLog)
                    .stream()
                    .sorted(Comparator.comparingInt(InterfaceLog::getInterfaceCount))
                    .toList();
            list.forEach(interfaceLog -> service.getOptById(interfaceLog.getLogId()));
            service.save(list.get(list.size()-1));
        }
        if(null != one){
            one.setInterfaceCount(one.getInterfaceCount()+1);
            service.update(one, upLog);
        }else{
            service.initDataBase();
        }

    }

    @Autowired
    public AopAdvice(InterfaceLogService service) {
        this.service = service;
    }
}
