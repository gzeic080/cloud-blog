package cn.xiaojunyun.cloud.blog.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * TODO: 类描述
 *
 * @author junyunxiao
 * @version 1.0
 * @date 2018/11/5 11:52
 */
@SpringBootApplication
@EnableEurekaServer
public class BlogEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogEurekaApplication.class, args);
    }
}
