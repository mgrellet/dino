package com.dinosaur.infraestructure.adapter.in;

import com.dinosaur.application.port.in.DinosaurService;
import com.dinosaur.domain.Dinosaur;
import com.dinosaur.domain.Status;
import com.dinosaur.infrastructure.adapter.in.DinosaurController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DinosaurController.class)
public class DinosaurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DinosaurService dinosaurService;

    @Autowired
    private ObjectMapper objectMapper;

    private Dinosaur dinosaur;

    private List<Dinosaur> list;

    @BeforeEach
    public void beforeEach() {
        dinosaur = Dinosaur.builder()
                .id(1L)
                .name("Dinosaur")
                .discoveryDate(LocalDateTime.now().minusDays(24))
                .extinctionDate(LocalDateTime.now())
                .species("Dinosaur")
                .status(Status.ALIVE)
                .build();
        list = List.of(dinosaur);
    }

    @Test
    void testCreateDinosaur() throws Exception {

        when(dinosaurService.createDinosaur(any(Dinosaur.class))).thenReturn(dinosaur);

        mockMvc.perform(post("/dinosaur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dinosaur)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is("Dinosaur")))
                .andExpect(jsonPath("$.data.species", is("Dinosaur")))
                .andExpect(jsonPath("$.data.status", is("ALIVE")));
    }

    @Test
    void testCreateDinosaur_Error() throws Exception {
        doThrow(new RuntimeException())
                .when(dinosaurService).createDinosaur(any(Dinosaur.class));
        mockMvc.perform(post("/dinosaur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dinosaur)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error while creating Dinosaur")));
    }

    @Test
    void testGetAll() throws Exception {

        Mockito.when(dinosaurService.getAll()).thenReturn(list);

        mockMvc.perform(get("/dinosaur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.[0].name", is("Dinosaur")))
                .andExpect(jsonPath("$.data.[0].species", is("Dinosaur")))
                .andExpect(jsonPath("$.data.[0].status", is("ALIVE")));
    }

    @Test
    void testGetAll_Error() throws Exception {
        doThrow(new RuntimeException())
                .when(dinosaurService).getAll();
        mockMvc.perform(get("/dinosaur"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error while getting Dinosaurs")));
    }

    @Test
    void getById() throws Exception {

        Mockito.when(dinosaurService.getById(anyLong())).thenReturn(dinosaur);

        mockMvc.perform(get("/dinosaur/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Dinosaur")))
                .andExpect(jsonPath("$.data.species", is("Dinosaur")))
                .andExpect(jsonPath("$.data.status", is("ALIVE")));
    }

    @Test
    void getById_Error() throws Exception {
        doThrow(new RuntimeException()).when(dinosaurService).getById(anyLong());
        mockMvc.perform(get("/dinosaur/{id}", 1L))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error while getting Dinosaur")));
    }

    @Test
    void testUpdate() throws Exception {
        Dinosaur updated = Dinosaur.builder()
                .id(1L)
                .name("Dinosaur updated")
                .discoveryDate(LocalDateTime.now().minusDays(24))
                .extinctionDate(LocalDateTime.now())
                .species("Dinosaur updated")
                .status(Status.ALIVE)
                .build();
        when(dinosaurService.update(anyLong(), any(Dinosaur.class))).thenReturn(updated);

        mockMvc.perform(put("/dinosaur/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dinosaur)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Dinosaur updated")))
                .andExpect(jsonPath("$.data.species", is("Dinosaur updated")))
                .andExpect(jsonPath("$.data.status", is("ALIVE")));
    }

    @Test
    void testUpdate_Error() throws Exception {
        doThrow(new RuntimeException()).when(dinosaurService).update(anyLong(), any(Dinosaur.class));
        mockMvc.perform(put("/dinosaur/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dinosaur)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error while updating Dinosaur")));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/dinosaur/{id}", 1L))
                .andExpect(status().isNoContent());
        Mockito.verify(dinosaurService, times(1)).delete(1L);
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(dinosaurService).delete(anyLong());
        mockMvc.perform(delete("/dinosaur/{id}", 1L))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error while deleting Dinosaur")));
    }

}
