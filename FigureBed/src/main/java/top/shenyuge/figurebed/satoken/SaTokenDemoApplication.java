package top.shenyuge.figurebed.satoken;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 制冷
 * @date 2024/3/28 20:01
 * @description SaTokenDemoApplication
 */
@SpringBootApplication
public class SaTokenDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaTokenDemoApplication.class, args);
        System.out.println("启动成功配置如下：" + SaManager.getConfig());
    }
}
