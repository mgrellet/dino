package com.dinosaur.infrastructure.entity;

import com.dinosaur.domain.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "dinosaurs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DinosaurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String species;
    private LocalDateTime discoveryDate;
    private LocalDateTime extinctionDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
