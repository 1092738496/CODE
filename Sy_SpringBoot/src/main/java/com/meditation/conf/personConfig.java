package com.meditation.conf;

import com.meditation.pojo.cat;
import com.meditation.pojo.dog;
import com.meditation.pojo.person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @time: 2024/7/11 15:15
 * @description:
 */

@Configuration
public class personConfig {
    @Bean
    public person Person(){
        return new person("张三", "男","25");
    }

    @Bean
    public dog Dog(){
        return new dog("小狗", "2");
    }

    @Bean
    public cat Cat(){
        return new cat("小狗", "2");
    }

}
