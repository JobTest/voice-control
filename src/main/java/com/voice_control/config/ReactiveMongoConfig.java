package com.voice_control.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories("com.voice_control.repository")
public class ReactiveMongoConfig {

}
