package com.project.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.response.OngoingMatchDto;
import com.project.mapper.OngoingMatchMapper;
import com.project.model.OngoingMatch;
import redis.clients.jedis.RedisClient;

import java.util.Optional;
import java.util.UUID;

public class RedisMatchStorage implements MatchStorage {
    private final ObjectMapper objectMapper;
    private final RedisClient redisClient;
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;

    public RedisMatchStorage(ObjectMapper objectMapper, RedisClient redisClient) {
        this.objectMapper = objectMapper;
        this.redisClient = redisClient;
    }

    @Override
    public void saveMatch(OngoingMatch match) {
        try {
            String key = String.valueOf(match.getUuid());
            OngoingMatchDto dto = mapper.toDto(match);
            String json = objectMapper.writeValueAsString(dto);
            redisClient.set(key, json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<OngoingMatch> getMatch(UUID uuid) {
        try {
            String key = String.valueOf(uuid);
            String json = redisClient.get(key);
            if (json == null || json.isBlank()) {
                return Optional.empty();
            }
            OngoingMatchDto dto = objectMapper.readValue(json, OngoingMatchDto.class);
            OngoingMatch match = mapper.toModel(dto);
            match.setUuid(uuid);
            return Optional.of(match);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMatch(UUID uuid) {
        try {
            String key = String.valueOf(uuid);
            redisClient.del(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
