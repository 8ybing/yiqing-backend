package cn.bybing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling // 开启定时任务功能
@SpringBootApplication
public class CovidWebBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidWebBackendApplication.class, args);
    }

}
