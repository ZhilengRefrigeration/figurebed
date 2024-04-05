package top.shenyuge.figurebed.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 制冷
 * @date 2024/3/31 17:28
 * @description SaTokenConfigure
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {StpUtil.checkLogin();}))
                .addPathPatterns("/**")
                .excludePathPatterns("/master/login", "/pin/show", "/ran/randomShow", "/ran/show");

    }
}
