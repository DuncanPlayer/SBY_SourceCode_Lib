package com.mtons.mblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * SprintBootApplication
 */
@Slf4j
@SpringBootApplication
@EnableCaching
public class BootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(BootApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("mblog started at http://107.182.184.86:" + serverPort);
    }

}