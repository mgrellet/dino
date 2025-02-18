package com.dinosaur.infrastructure.adapter.in;

import com.dinosaur.application.port.in.DinosaurService;
import com.dinosaur.domain.Dinosaur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/dinosaur")
public class DinosaurController {
    private final DinosaurService dinosaurService;

    public DinosaurController(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @PostMapping
    public ResponseEntity<Dinosaur> createDinosaur(@RequestBody Dinosaur dinosaur) {
        Dinosaur createdDinosaur = dinosaurService.createDinosaur(dinosaur);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDinosaur.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdDinosaur);
    }

    @GetMapping
    public ResponseEntity<List<Dinosaur>> getAll() {
        return ResponseEntity.ok(dinosaurService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dinosaur> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dinosaurService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dinosaur> update(@PathVariable Long id,
                                           @RequestBody Dinosaur dinosaur) {
        return ResponseEntity.ok(dinosaurService.update(id, dinosaur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dinosaurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
