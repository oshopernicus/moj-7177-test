package uk.gov.hmcts.reform.dev.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("Test Description");
        sampleTask.setStatus("Pending");
        sampleTask.setCreatedAt(LocalDateTime.of(2025, 7, 28, 0, 0));
        sampleTask.setUpdatedAt(LocalDateTime.of(2025, 7, 28, 0, 0));
    }

    @Test
    @DisplayName("createTask: success")
    void createTask_success() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Task created = taskService.createTask(sampleTask);
        assertThat(created).isNotNull();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("createTask: duplicate title")
    void createTask_duplicateTitle() {
        Task existing = new Task();
        existing.setTitle("Test Task");
        when(taskRepository.findAll()).thenReturn(List.of(existing));
        assertThatThrownBy(() -> taskService.createTask(sampleTask))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unique");
    }

    @Test
    @DisplayName("createTask: invalid title")
    void createTask_invalidTitle() {
        sampleTask.setTitle("");
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());
        assertThatThrownBy(() -> taskService.createTask(sampleTask))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title is required");
    }

    @Test
    @DisplayName("getTaskById: found")
    void getTaskById_found() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        Optional<Task> result = taskService.getTaskById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getTaskById: not found")
    void getTaskById_notFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Task> result = taskService.getTaskById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("getAllTasks: returns all tasks")
    void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(sampleTask));
        List<Task> tasks = taskService.getAllTasks();
        assertThat(tasks).hasSize(1);
    }

    @Test
    @DisplayName("updateTask: success")
    void updateTask_success() {
        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("Updated desc");
        updated.setStatus("Done");
        updated.setDueDateTime(LocalDateTime.of(2025, 8, 1, 0, 0));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);
        Task result = taskService.updateTask(1L, updated);
        assertThat(result).isNotNull();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("updateTask: not found")
    void updateTask_notFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Task updated = new Task();
        updated.setTitle("Updated");
        assertThat(taskService.updateTask(1L, updated)).isNull();
    }

    @Test
    @DisplayName("updateTask: invalid description")
    void updateTask_invalidDescription() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("");
        updated.setStatus("Done");
        assertThatThrownBy(() -> taskService.updateTask(1L, updated))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Description is required");
    }

    @Test
    @DisplayName("deleteTask: calls repository")
    void deleteTask() {
        taskService.deleteTask(1L);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    @DisplayName("getTasksByStatuses: returns filtered tasks")
    void getTasksByStatuses() {
        when(taskRepository.findByStatusIn(List.of("Pending"))).thenReturn(List.of(sampleTask));
        List<Task> tasks = taskService.getTasksByStatuses(List.of("Pending"));
        assertThat(tasks).hasSize(1);
    }
}
