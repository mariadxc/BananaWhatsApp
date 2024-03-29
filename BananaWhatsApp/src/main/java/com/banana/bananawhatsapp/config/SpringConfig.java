package com.banana.bananawhatsapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import({RepoConfig.class, ControllerConfig.class, ServConfig.class})
@Import({RepoConfig.class, ControllerConfig.class})
//@ComponentScan({"com.banana.bananawhatsapp.persistencia"})
@ComponentScan({"com.banana.bananawhatsapp.servicios"})
public class SpringConfig {

}
