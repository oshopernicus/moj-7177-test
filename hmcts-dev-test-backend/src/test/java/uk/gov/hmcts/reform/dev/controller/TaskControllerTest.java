package uk.gov.hmcts.reform.dev.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TaskControllerTest.MockConfig.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TaskService taskService() {
            return org.mockito.Mockito.mock(TaskService.class);
        }
    }

    private Task sampleTask() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus("Pending");
        task.setCreatedAt(LocalDateTime.of(2025, 7, 28, 0, 0));
        task.setUpdatedAt(LocalDateTime.of(2025, 7, 28, 0, 0));
        return task;
    }

    @Test
    @DisplayName("POST /api/tasks - success")
    void createTask_success() throws Exception {
        Task task = sampleTask();
        org.mockito.Mockito.when(taskService.createTask(any(Task.class))).thenReturn(task);
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    @DisplayName("POST /api/tasks - validation error")
    void createTask_validationError() throws Exception {
        org.mockito.Mockito.when(taskService.createTask(any(Task.class))).thenThrow(new IllegalArgumentException("Invalid"));
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTask())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/tasks/{id} - found")
    void getTask_found() throws Exception {
        Task task = sampleTask();
        org.mockito.Mockito.when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} - not found")
    void getTask_notFound() throws Exception {
        org.mockito.Mockito.when(taskService.getTaskById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/tasks - all tasks")
    void getAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(sampleTask());
        org.mockito.Mockito.when(taskService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("PUT /api/tasks/{id} - update success")
    void updateTask_success() throws Exception {
        Task updated = sampleTask();
        updated.setTitle("Updated");
        org.mockito.Mockito.when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updated);
        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    @DisplayName("PUT /api/tasks/{id} - not found")
    void updateTask_notFound() throws Exception {
        org.mockito.Mockito.when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(null);
        mockMvc.perform(put("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTask())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/tasks/{id}")
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }
}
