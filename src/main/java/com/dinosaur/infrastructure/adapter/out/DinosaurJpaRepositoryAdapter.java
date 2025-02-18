package com.dinosaur.infrastructure.adapter.out;

import com.dinosaur.application.port.out.DinosaurRepositoryPort;
import com.dinosaur.domain.Dinosaur;
import com.dinosaur.domain.Status;
import com.dinosaur.infrastructure.entity.DinosaurEntity;
import com.dinosaur.infrastructure.mapper.DinosaurEntityMapper;
import com.dinosaur.infrastructure.repository.DinosaurJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Component
@Slf4j
public class DinosaurJpaRepositoryAdapter implements DinosaurRepositoryPort {

    private final DinosaurJpaRepository jpaRepository;

    private final Publisher publisher;

    public DinosaurJpaRepositoryAdapter(DinosaurJpaRepository jpaRepository, Publisher publisher) {
        this.jpaRepository = jpaRepository;
        this.publisher = publisher;
    }

    @Override
    public Dinosaur create(Dinosaur dinosaur) {
        validateDinosaurCreation(dinosaur);
        DinosaurEntity entity = DinosaurEntityMapper.toEntity(dinosaur);
        return DinosaurEntityMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public List<Dinosaur> getAll() {
        List<DinosaurEntity> entities = jpaRepository.findAll();
        return entities.stream().map(DinosaurEntityMapper::toDomain).toList();
    }

    @Override
    public Dinosaur getById(Long id) {
        DinosaurEntity dinosaurEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Dinosaur not found"));

        return DinosaurEntityMapper.toDomain(dinosaurEntity);
    }

    @Override
    public Dinosaur update(Long id, Dinosaur dinosaur) {
        DinosaurEntity dinosaurEntity = jpaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Dinosaur not found"));

        if (dinosaurEntity.getStatus() == Status.EXTINCT) {
            throw new IllegalArgumentException("Cannot update extinct dinosaur");
        }

        dinosaurEntity.setName(dinosaur.getName());
        dinosaurEntity.setSpecies(dinosaur.getSpecies());
        dinosaurEntity.setDiscoveryDate(dinosaur.getDiscoveryDate());
        dinosaurEntity.setExtinctionDate(dinosaur.getExtinctionDate());
        dinosaurEntity.setStatus(dinosaur.getStatus());
        return DinosaurEntityMapper.toDomain(jpaRepository.save(dinosaurEntity));
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    private void validateDinosaurCreation(Dinosaur dinosaur) {
        DinosaurEntity dinosaurEntity = jpaRepository.findByName(dinosaur.getName());
        if (dinosaurEntity != null) {
            throw new IllegalArgumentException("Dinosaur already exists");
        }

        if (dinosaur.getDiscoveryDate().isAfter(dinosaur.getExtinctionDate())) {
            throw new IllegalArgumentException("Discovery date must be before extinction date");
        }

        if (dinosaur.getStatus() != Status.ALIVE) {
            throw new IllegalArgumentException("Initial status should be alive");
        }
    }

    @Scheduled(fixedRate = 600000) // Cada 10 minutos
    public void updateDinosaurStatus() {
        LocalDateTime now = LocalDateTime.now();
        log.info("Updating dinosaur status");
        List<DinosaurEntity> dinosaurEntities = jpaRepository.findAll();

        for (DinosaurEntity dino : dinosaurEntities) {
            LocalDateTime aDayBefore = now.minusDays(1);
            if (dino.getStatus() == Status.ALIVE
                    && (dino.getExtinctionDate().isAfter(aDayBefore) || dino.getExtinctionDate().isEqual(aDayBefore))
                    && (dino.getExtinctionDate().isBefore(now))) {
                dino.setStatus(Status.ENDANGERED);
                jpaRepository.save(dino);
                sendMessageToQueue(dino);
            }

            if (dino.getExtinctionDate().isAfter(now) || dino.getExtinctionDate().equals(now)) {
                dino.setStatus(Status.EXTINCT);
                jpaRepository.save(dino);
                sendMessageToQueue(dino);
            }
        }
    }

    private void sendMessageToQueue(DinosaurEntity dinosaurEntity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);
        MessageDto messageDto = MessageDto.builder()
                .dinosaurId(dinosaurEntity.getId())
                .newStatus(dinosaurEntity.getStatus())
                .timestamp(formattedDate).build();
        publisher.sendMessage(messageDto);
    }

}
