package com.dinosaur.application.port.out;

import com.dinosaur.domain.Dinosaur;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DinosaurRepositoryPort {
    Dinosaur create(Dinosaur dinosaur);
    List<Dinosaur> getAll();
    Dinosaur getById(Long id);
    Dinosaur update(Long id, Dinosaur dinosaur);
    void delete(Long id);
}
