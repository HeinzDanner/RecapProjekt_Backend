package de.heinzdanner.recapprojekt_backend.todo;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import de.heinzdanner.recapprojekt_backend.todo.history.HistoryService;

@SpringBootTest
@AutoConfigureMockMvc
class UndoRedoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HistoryService historyService;

    @BeforeEach
    void setup() {
        todoRepository.deleteAll();
        historyService.clear();
    }

    @Test
    void undo_should_revert_last_change() throws Exception {
        // create
        String createBody = """
                {
                  "description": "Undo Test",
                  "status": "OPEN"
                }
                """;

        String response = mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo created = objectMapper.readValue(response, Todo.class);

        // check exists
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // undo create -> should remove todo
        mockMvc.perform(post("/api/undo"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // optional: undo again -> nothing to undo (conflict)
        mockMvc.perform(post("/api/undo"))
                .andExpect(status().isConflict());
    }

    @Test
    void redo_should_reapply_undone_change() throws Exception {
        // create
        String createBody = """
                {
                  "description": "Redo Test",
                  "status": "OPEN"
                }
                """;

        mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated());

        // undo create
        mockMvc.perform(post("/api/undo"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // redo create
        mockMvc.perform(post("/api/redo"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}