package com.project.storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.domain.OngoingMatch;
import com.project.dto.response.OngoingMatchDto;
import com.project.exception.DataNotFoundException;
import com.project.exception.StorageException;
import com.project.mapper.OngoingMatchMapper;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;

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

    @Override
    public OngoingMatch update(UUID uuid, UnaryOperator<OngoingMatch> updater) {
        try (Connection connection = redisClient.getPool().getResource();
             Jedis jedis = new Jedis(connection)) {

            String key = String.valueOf(uuid);
            while (true) {
                jedis.watch(key);
                OngoingMatch match = getActualMatch(jedis, key);
                OngoingMatch updatedMatch = updater.apply(match);
                String json = toJson(updatedMatch);
                Transaction transaction = jedis.multi();
                transaction.set(key, json);
                List<Object> result = transaction.exec();
                if (result != null && !result.isEmpty()) {
                    return updatedMatch;
                }
            }

        } catch (JedisException e) {
            throw new StorageException("Failed to update match at the storage", e);
        } catch (Exception e) {
            throw new IllegalStateException("An unknown error occurred while working with the storage", e);
        }
    }

    private OngoingMatch getActualMatch(Jedis jedis, String key) throws JsonProcessingException {
        String json = jedis.get(key);
        if (json == null || json.isEmpty()) {
            jedis.unwatch();
            throw new DataNotFoundException("Couldn't find the current match");
        }
        OngoingMatchDto matchDto = objectMapper.readValue(json, OngoingMatchDto.class);
        return mapper.toModel(matchDto);
    }

    private String toJson(OngoingMatch match) throws JsonProcessingException {
        OngoingMatchDto dto = mapper.toDto(match);
        return objectMapper.writeValueAsString(dto);
    }
}
