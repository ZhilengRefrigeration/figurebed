package top.shenyuge.figurebed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author 制冷
 * @date 2024/4/19 下午7:59
 * @description SchedulingConfiguration
 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration {
    @Bean()
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(10);
    }
}