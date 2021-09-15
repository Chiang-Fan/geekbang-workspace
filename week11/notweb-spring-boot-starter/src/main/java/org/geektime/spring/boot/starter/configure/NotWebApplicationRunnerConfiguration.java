package org.geektime.spring.boot.starter.configure;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author FanJiang
 * @since
 */
@Configuration
@ConditionalOnNotWebApplication
public class NotWebApplicationRunnerConfiguration {

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.println("Hello World!!");
    }
}
