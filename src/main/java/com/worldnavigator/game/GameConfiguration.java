package com.worldnavigator.game;

import com.worldnavigator.game.controls.Invoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfiguration {

    @Bean
    public Invoker invoker(){
        return new Invoker();
    }
}
