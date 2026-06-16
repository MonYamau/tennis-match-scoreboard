package com.project.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.OngoingMatch;
import com.project.dto.response.OngoingMatchDto;
import com.project.exception.StorageException;
import com.project.mapper.OngoingMatchMapper;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RedisMatchStorage implements MatchStorage {
    private final ObjectMapper objectMapper;
    private final RedisClient redisClient;
    private final OngoingMatchMapper mapper = OngoingMatchMapper.INSTANCE;

    @Override
    public void save(OngoingMatch match) {
        try {
            String key = String.valueOf(match.getUuid());
            OngoingMatchDto dto = mapper.toDto(match);
            String json = objectMapper.writeValueAsString(dto);
            redisClient.set(key, json);

        } catch (JedisException | JsonProcessingException e) {
            throw new StorageException("Failed to save match to the storage", e);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the storage", e);
        }
    }

    @Override
    public Optional<OngoingMatch> find(UUID uuid) {
        try {
            String key = String.valueOf(uuid);
            String json = redisClient.get(key);
            if (json == null || json.isBlank()) {
                return Optional.empty();
            }
            OngoingMatchDto dto = objectMapper.readValue(json, OngoingMatchDto.class);
            OngoingMatch match = mapper.toModel(dto);
            return Optional.of(match);

        } catch (JedisException | JsonProcessingException e) {
            throw new StorageException("Failed to find match from the storage", e);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the storage", e);
        }
    }

    @Override
    public void delete(UUID uuid) {
        try {
            String key = String.valueOf(uuid);
            redisClient.del(key);

        } catch (JedisException e) {
            throw new StorageException("Failed to delete match from the storage", e);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the storage", e);
        }
    }
}
