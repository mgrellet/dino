package com.dinosaur.application.service;

import com.dinosaur.application.port.out.DinosaurRepositoryPort;
import com.dinosaur.domain.Dinosaur;
import com.dinosaur.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DinosaurServiceImplTest {

    @Mock
    DinosaurRepositoryPort dinosaurRepositoryPort;

    @InjectMocks
    DinosaurServiceImpl dinosaurService;

    Dinosaur dinosaur;

    @BeforeEach
    void setUp() {
        dinosaur = Dinosaur.builder()
                .id(1L)
                .name("Dinosaur")
                .discoveryDate(LocalDateTime.now().minusDays(24))
                .extinctionDate(LocalDateTime.now())
                .species("Dinosaur")
                .status(Status.ALIVE)
                .build();
    }

    @Test
    void testCreateDinosaur() {
        when(dinosaurRepositoryPort.create(any(Dinosaur.class))).thenReturn(dinosaur);
        Dinosaur created = dinosaurRepositoryPort.create(dinosaur);
        assertNotNull(created);
        verify(dinosaurRepositoryPort, times(1)).create(any(Dinosaur.class));
    }

    @Test
    void testGetAll() {
        List<Dinosaur> dinosaurList = Collections.singletonList(dinosaur);
        when(dinosaurRepositoryPort.getAll()).thenReturn(dinosaurList);
        List<Dinosaur> result = dinosaurService.getAll();
        assertEquals(1, result.size());
        verify(dinosaurRepositoryPort, times(1)).getAll();
    }

    @Test
    void testGetById() {
        Long id = dinosaur.getId();
        when(dinosaurRepositoryPort.getById(id)).thenReturn(dinosaur);
        Dinosaur result = dinosaurService.getById(id);
        assertEquals("Dinosaur", result.getName());
        verify(dinosaurRepositoryPort, times(1)).getById(id);
    }

    @Test
    void testUpdateSuccess() {
        Dinosaur updated = Dinosaur.builder()
                .id(1L)
                .name("Dinosaur updated")
                .discoveryDate(LocalDateTime.now().minusDays(24))
                .extinctionDate(LocalDateTime.now())
                .species("Dinosaur updated")
                .status(Status.ALIVE)
                .build();
        when(dinosaurRepositoryPort.update(dinosaur.getId(), dinosaur)).thenReturn(updated);
        Dinosaur updatedDinosaur = dinosaurService.update(dinosaur.getId(), dinosaur);

        assertEquals(updated.getName(), updatedDinosaur.getName());
        assertEquals(updated.getSpecies(), updatedDinosaur.getSpecies());

        verify(dinosaurRepositoryPort, times(1)).update(dinosaur.getId(), dinosaur);
    }

    @Test
    void testDelete() {
        Long id = dinosaur.getId();
        dinosaurService.delete(id);
        verify(dinosaurRepositoryPort, times(1)).delete(id);
    }

}
