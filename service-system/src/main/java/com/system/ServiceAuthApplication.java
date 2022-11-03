package com.system;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类描述：
 *
 * @ClassName ServiceAuthApplication
 * @Description TODO
 * @Author Ming
 * @Date 2022/10/28 14:35
 * @Version 1.0
 */
@SpringBootApplication
@Slf4j
@MapperScan("com.system.mapper")
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class,args);
        log.info("服务启动成功");
    }
}
