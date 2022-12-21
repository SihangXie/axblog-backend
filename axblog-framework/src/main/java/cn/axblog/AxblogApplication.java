package cn.axblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Package: cn.axblog Description： Author: Sihang Xie Date: Created in ${DATE} ${TIME} Company:
 * Ocean University of China Copyright: Copyright (c) 2022 Version: 0.0.1 Modified By:
 */
@SpringBootApplication
@Slf4j
public class AxblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AxblogApplication.class);
        log.info("项目启动成功");
    }
}