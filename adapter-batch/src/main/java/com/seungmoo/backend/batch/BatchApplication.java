package com.seungmoo.backend.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.sql.DataSource;

@Slf4j
@EnableBatchProcessing
@SpringBootApplication(scanBasePackages = "com.seungmoo")
public class BatchApplication extends DefaultBatchConfigurer {
    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
                new SpringApplicationBuilder(BatchApplication.class)
                        .web(WebApplicationType.NONE)
                        .bannerMode(Banner.Mode.LOG)
                        .build(args)
                        .run(args)
        ));
    }

    @Override
    public void setDataSource(DataSource dataSource) {}
}
