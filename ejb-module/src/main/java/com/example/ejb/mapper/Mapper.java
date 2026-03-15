package com.example.ejb.mapper;

public interface Mapper<Entity, DTO> {

    Entity toEntity(DTO dto);
    DTO toDTO(Entity entity);
}
