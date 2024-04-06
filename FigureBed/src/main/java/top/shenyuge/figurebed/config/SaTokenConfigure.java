package top.shenyuge.figurebed.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author 制冷
 * @date 2024/3/31 17:28
 * @description SaTokenConfigure
 */
@Configuration
public class SaTokenConfigure{
    // 注册拦截器
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 拦截与排除 path
                .addInclude("/**").addExclude("/favicon.ico","/pin/show", "/ran/show", "ran/randomShow", "pin/show")
                .setAuth(obj -> {
                    SaRouter.match("/**","/master/login", StpUtil::checkLogin);

                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> SaResult.error(e.getMessage()))
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "*")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
//                            .setHeader("Content-Type","application/json")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .back();
                })
                ;
    }

}
