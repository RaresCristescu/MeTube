package com.app.data.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public abstract class BaseMapper<D, E> {

    // Entity → DTO
    public abstract D toDto(E entity);

    // DTO → Entity
    public abstract E toEntity(D dto);

    // Update existing entity (JPA-safe)
    public abstract void updateEntityFromDto(D dto, @MappingTarget E entity);

    // List helpers (shared logic)
    public List<D> toDtoList(List<E> entities) {
        return entities == null
                ? List.of()
                : entities.stream().map(this::toDto).toList();
    }

    public List<E> toEntityList(List<D> dtos) {
        return dtos == null
                ? List.of()
                : dtos.stream().map(this::toEntity).toList();
    }
}

