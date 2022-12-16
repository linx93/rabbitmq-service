package net.phadata.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author top
 */
@Slf4j
@DynamicInsert
@DynamicUpdate
@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties
public class RabbitmqServiceApplication implements ApplicationRunner {

    @Value("${project.version}")
    private String version;
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqServiceApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("项目启动完成-");
        log.info("version: " + version);
    }
}
