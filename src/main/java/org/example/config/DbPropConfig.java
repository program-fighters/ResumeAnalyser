package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Configuration
public class DbPropConfig {

    private final Environment config;

    @Autowired
    public DbPropConfig(Environment config) {
        this.config = config;
    }


    @Bean
    public MongodbProperties mongodbProperties() {
        MongodbProperties prop = new MongodbProperties();
        prop.setUri(config.getProperty("spring.data.mongodb.uri"));
        prop.setDatabase(config.getProperty("spring.data.mongodb.database"));
        prop.setPassword(readPasswordAndConvertInArray(config.getProperty("spring.data.mongodb.password")));
        prop.setUsername(config.getProperty("spring.data.mongodb.username"));
        prop.setAuthenticationDatabase(config.getProperty("spring.data.mongodb.authenticationDatabase"));
        prop.setHost(config.getProperty("spring.data.mongodb.host"));
        prop.setPort(Integer.parseInt(Objects.requireNonNull(config.getProperty("spring.data.mongodb.port"))));
        prop.setSocketTimeout(60000);
        prop.setConnectTimeout(60000);
        return prop;
    }


    private char[] readPasswordAndConvertInArray(String pass) {
        if (!ObjectUtils.isEmpty(pass)) {
            return pass.toCharArray();
        }
        return new char[0];
    }

}
