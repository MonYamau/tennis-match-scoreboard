package com.project.factory;

import com.project.exception.ConfigurationException;
import com.project.util.PropertiesUtil;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.RedisClient;

import java.util.Properties;

@UtilityClass
public class RedisFactory {
    private final Logger log = LoggerFactory.getLogger(RedisFactory.class);

    public RedisClient create() {
        try {
            RedisClient redisClient = initialize();
            redisClient.ping();
            return redisClient;
        } catch (Exception e) {
            log.error("Failed to initialize redisClient", e);
            throw new ConfigurationException("Failed to initialize redisClient", e);
        }
    }

    private RedisClient initialize() {
        Properties properties = PropertiesUtil.getProperties("storage.properties");
        String host = properties.getProperty("redis.host");
        int port;
        try {
            port = Integer.parseInt(properties.getProperty("redis.port"));
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Failed to get port for Redis", e);
        }
        return RedisClient.builder().hostAndPort(host, port).build();
    }
}
