package com.licheng.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Arrays;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan("com.licheng.sample.mapper")
public class SampleApplication extends SpringBootServletInitializer {

    private static Logger logger;

    static {
        logger = LoggerFactory.getLogger(SampleApplication.class);
    }

    public static void main(String[] args) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(timeZone);

        logger.info("============= 运行参数如下: =============");
        Arrays.stream(args).forEach(data -> logger.info(data));
        SpringApplication.run(SampleApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SampleApplication.class);
    }

}
