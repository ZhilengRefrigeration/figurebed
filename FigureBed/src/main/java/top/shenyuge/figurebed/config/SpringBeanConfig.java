package top.shenyuge.figurebed.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 制冷
 * @date 2024/3/31 16:10
 * @description SpringBeanConfig
 */
@Configuration
public class SpringBeanConfig {
    @Bean
    public Gson gson(){
        return new Gson();
    }
}
