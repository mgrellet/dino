package com.dinosaur.infrastructure.mapper;

import com.dinosaur.domain.Dinosaur;
import com.dinosaur.infrastructure.entity.DinosaurEntity;

public class DinosaurEntityMapper {
    public static DinosaurEntity toEntity(Dinosaur dinosaur) {
        return DinosaurEntity.builder()
                .id(dinosaur.getId())
                .name(dinosaur.getName())
                .species(dinosaur.getSpecies())
                .discoveryDate(dinosaur.getDiscoveryDate())
                .extinctionDate(dinosaur.getExtinctionDate())
                .status(dinosaur.getStatus())
                .build();
    }

    public static Dinosaur toDomain(DinosaurEntity entity) {
        return Dinosaur.builder()
                .id(entity.getId())
                .name(entity.getName())
                .species(entity.getSpecies())
                .discoveryDate(entity.getDiscoveryDate())
                .extinctionDate(entity.getExtinctionDate())
                .status(entity.getStatus())
                .build();
    }
}
