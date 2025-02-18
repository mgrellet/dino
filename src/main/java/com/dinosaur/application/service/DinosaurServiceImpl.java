package com.dinosaur.application.service;

import com.dinosaur.application.port.in.DinosaurService;
import com.dinosaur.application.port.out.DinosaurRepositoryPort;
import com.dinosaur.domain.Dinosaur;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DinosaurServiceImpl implements DinosaurService {

    private final DinosaurRepositoryPort dinosaurRepositoryPort;

    public DinosaurServiceImpl(DinosaurRepositoryPort dinosaurRepositoryPort) {
        this.dinosaurRepositoryPort = dinosaurRepositoryPort;
    }

    @Override
    public Dinosaur createDinosaur(Dinosaur dinosaur) {
        return dinosaurRepositoryPort.create(dinosaur);
    }

    @Override
    public List<Dinosaur> getAll() {
        return dinosaurRepositoryPort.getAll();
    }

    @Override
    public Dinosaur getById(Long id) {
        return dinosaurRepositoryPort.getById(id);
    }

    @Override
    public Dinosaur update(Long id, Dinosaur dinosaur) {
        return dinosaurRepositoryPort.update(id, dinosaur);
    }

    @Override
    public void delete(Long id) {
        dinosaurRepositoryPort.delete(id);
    }
}
