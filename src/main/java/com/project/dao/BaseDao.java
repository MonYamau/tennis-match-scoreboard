package com.project.dao;

import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> add(T model);
}
