package org.example.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@Configuration
public class MongodbConfig {
    private static final List<String> DEVELOPER_SYSTEM_RUNNING_PROFILES = List.of("local");
    private final MongodbProperties properties;
    private final Environment environment;

    @Autowired
    public MongodbConfig(MongodbProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
    }


    @Bean
    public MongoClient mongodbClient() throws IOException {
        String profile = environment.getProperty("spring.profiles.active");
        if (DEVELOPER_SYSTEM_RUNNING_PROFILES.contains(profile)) {
            return MongoClients.create(properties.getUri());
        } else {
            MongoCredential credential = MongoCredential
                    .createCredential(
                            properties.getUsername(),
                            properties.getAuthenticationDatabase(),
                            properties.getPassword()
                    );
            MongoClientSettings settings = MongoClientSettings
                    .builder()
                    .credential(credential)
                    .applyToSocketSettings(builder ->
                            builder.readTimeout(properties.determineSessionTimeout(), TimeUnit.MILLISECONDS).connectTimeout(properties.determineConnectTimeout(), TimeUnit.MILLISECONDS))
                    .applyToClusterSettings(builder ->
                            builder.hosts(Collections.singletonList(new ServerAddress(properties.getHost(), properties.getPort()))))
                    .build();
            return MongoClients.create(settings);
        }
    }


    @Bean
    public MongoDatabase database() throws IOException {
        CodecRegistry extendedRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
        return mongodbClient().getDatabase(properties.getDatabase()).withCodecRegistry(extendedRegistry);
    }

    @PreDestroy
    public void destroy() throws IOException {
        mongodbClient().close();
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() throws Exception {
        return new SimpleMongoClientDatabaseFactory(mongodbClient(), properties.getDatabase());
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory());
        MongoCustomConversions conversions = new MongoCustomConversions(new ArrayList<>(0));
        MongoMappingContext mappingContext = new MongoMappingContext();
        mappingContext.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        mappingContext.afterPropertiesSet();
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mappingContext);
        converter.setCustomConversions(conversions);
        converter.setCodecRegistryProvider(mongoDatabaseFactory());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        converter.afterPropertiesSet();
        return converter;
    }


    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDatabaseFactory(), mappingMongoConverter());
    }


}
