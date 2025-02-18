package com.dinosaur.application.port.in;

import com.dinosaur.domain.Dinosaur;

import java.util.List;

public interface DinosaurService {
    Dinosaur createDinosaur(Dinosaur dinosaur);
    List<Dinosaur> getAll();
    Dinosaur getById(Long id);
    Dinosaur update(Long id, Dinosaur dinosaur);
    void delete(Long id);
}
