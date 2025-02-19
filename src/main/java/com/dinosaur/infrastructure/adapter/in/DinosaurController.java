package com.dinosaur.infrastructure.adapter.in;

import com.dinosaur.application.port.in.DinosaurService;
import com.dinosaur.domain.Dinosaur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Create a new Dinosaur")
    @ApiResponse(responseCode = "201", description = "Dinosaur created successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json"))
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

    @Operation(summary = "Gets the list of Dinosaurs")
    @ApiResponse(responseCode = "200", description = "Dinosaurs retrieved successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json"))
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

    @Operation(summary = "Gets specific Dinosaurs by id")
    @ApiResponse(responseCode = "200", description = "Dinosaur retrieved successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json"))
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

    @Operation(summary = "Update a specific Dinosaurs by id")
    @ApiResponse(responseCode = "200", description = "Dinosaur updated successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json"))
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

    @Operation(summary = "Delete a specific Dinosaurs by id")
    @ApiResponse(responseCode = "204", description = "Dinosaur deleted successfully",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(mediaType = "application/json"))
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
