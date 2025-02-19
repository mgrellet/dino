package com.dinosaur.infrastructure.adapter.in;

import com.dinosaur.application.port.in.DinosaurService;
import com.dinosaur.domain.Dinosaur;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/dinosaur")
public class DinosaurController {
    private final DinosaurService dinosaurService;

    public DinosaurController(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @PostMapping
    public ResponseEntity<Response> createDinosaur(@RequestBody Dinosaur dinosaur) {
        try {
            Dinosaur createdDinosaur = dinosaurService.createDinosaur(dinosaur);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdDinosaur.getId())
                    .toUri();
            Response response = Response.builder()
                    .data(createdDinosaur)
                    .message("success")
                    .status(HttpStatus.CREATED.value())
                    .build();

            return ResponseEntity.created(location).body(response);
        } catch (Exception e) {
            Response errorResponse = Response.builder()
                    .data(e.getMessage())
                    .message("Error while creating Dinosaur")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<Response> getAll() {
        try {
            Response response = Response.builder()
                    .data(dinosaurService.getAll())
                    .message("success")
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(e.getMessage())
                    .message("Error while getting Dinosaurs")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getById(@PathVariable Long id) {
        try {
            Response response = Response.builder()
                    .data(dinosaurService.getById(id))
                    .message("success")
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(e.getMessage())
                    .message("Error while getting Dinosaur")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id,
                                           @RequestBody Dinosaur dinosaur) {
        try {
            Response response = Response.builder()
                    .data(dinosaurService.update(id, dinosaur))
                    .message("success")
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(e.getMessage())
                    .message("Error while updating Dinosaur")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        try {
            dinosaurService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            Response errorResponse = Response.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .data(e.getMessage())
                    .message("Error while deleting Dinosaur")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }
}
