package com.worldnavigator.web;

import com.worldnavigator.web.services.MazeTemplateService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorldNavigatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldNavigatorApplication.class, args);
    }
}
