package com.epam.esm.dto.mapper;

public interface DTOMapper<T, K> {

    T toDTO(K k);

    K toEntity(T t);

}
