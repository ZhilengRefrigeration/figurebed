package top.shenyuge.figurebed.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.shenyuge.figurebed.bean.InterfaceLog;
import top.shenyuge.figurebed.service.InterfaceLogService;

import java.time.LocalDate;

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

        InterfaceLog one = service.getOne(getNowLog);
        if(null != one){
            one.setInterfaceCount(one.getInterfaceCount()+1);
            service.update(one, upLog);
        }else{
            service.save(new InterfaceLog(interfaceName, LocalDate.now(), 1));
        }


    }

    @Autowired
    public AopAdvice(InterfaceLogService service) {
        this.service = service;
    }
}
