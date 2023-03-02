package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.Optional;

public interface SimpleDAO<T, K> {

    T create(T t);

    Optional<T> findById(K id);

    boolean delete(K id);

    T update(T t);
}
