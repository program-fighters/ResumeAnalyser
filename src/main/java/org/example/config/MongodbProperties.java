package org.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongodbProperties extends MongoProperties {
    private Integer socketTimeout;
    @Getter
    private Integer connectTimeout;

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public int determineConnectTimeout() {
        return (connectTimeout == null) ? 5000 : connectTimeout;
    }

    public int determineSessionTimeout() {
        return (socketTimeout == null) ? 5000 : socketTimeout;
    }
}
