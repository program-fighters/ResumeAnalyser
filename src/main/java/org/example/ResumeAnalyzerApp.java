package org.example;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.example.db.repo.support.MongodbRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static org.example.db.repo.MongodbRepository.MONGODB_REPO_PCK_NAME;

@EnableEncryptableProperties
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        RedisAutoConfiguration.class,
        ElasticsearchDataAutoConfiguration.class,
        ElasticsearchRepositoriesAutoConfiguration.class
})
@EnableMongoRepositories(repositoryBaseClass = MongodbRepositoryImpl.class, considerNestedRepositories = true, basePackages = MONGODB_REPO_PCK_NAME)

public class ResumeAnalyzerApp {
    public static void main(String[] args) {
        SpringApplication.run(ResumeAnalyzerApp.class, args);
    }
}
