package cn.xiaojunyun.cloud.blog.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * TODO: 用户微服务启动类
 *
 * @author junyunxiao
 * @version 1.0
 * @date 2018/11/5 11:52
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = { "cn.xiaojunyun.cloud.blog" })
public class BlogUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogUserApplication.class, args);
    }
}
