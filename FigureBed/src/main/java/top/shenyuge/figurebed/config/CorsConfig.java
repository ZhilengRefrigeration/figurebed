package top.shenyuge.figurebed.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * @author 制冷
 * @date 2024/4/2 16:13
 * @description CorsConfig
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域访问的源，* 表示所有
        config.addAllowedOrigin("*");
        // 允许跨域访问的请求方法
        config.addAllowedMethod("*");
        // 允许跨域访问的请求头
        config.addAllowedHeader("*");
        // 允许发送Cookie
        config.setAllowCredentials(false);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
