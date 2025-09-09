package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
        ElasticsearchDataAutoConfiguration.class,
        ElasticsearchRepositoriesAutoConfiguration.class
})
public class ResumeAnalyzerApp {
    public static void main(String[] args) {
        System.out.println("Welcome to ResumeAnalyzerApp. !!!!!!!!!!!!!!!");
        SpringApplication.run(ResumeAnalyzerApp.class, args);
    }
}
