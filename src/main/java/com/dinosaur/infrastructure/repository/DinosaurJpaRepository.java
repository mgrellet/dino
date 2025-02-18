package com.dinosaur.infrastructure.repository;

import com.dinosaur.infrastructure.entity.DinosaurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DinosaurJpaRepository extends JpaRepository<DinosaurEntity, Long> {
    DinosaurEntity findByName(String name);
}
