package uk.gov.hmcts.reform.dev.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private static final int MAX_TITLE_LENGTH = 255;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;


    /**
     * Creates a new task after validating all business rules.
     *
     * @param task the task to create
     * @return the created task
     * @throws IllegalArgumentException if validation fails
     */
    public Task createTask(Task task) {
        LocalDateTime now = LocalDateTime.of(2025, 7, 28, 0, 0); // Use provided current date
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        validateTask(task, true);
        return taskRepository.save(task);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task
     * @return an Optional containing the task if found, or empty if not
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Updates an existing task after validating all business rules.
     *
     * @param id the ID of the task to update
     * @param updatedTask the updated task object
     * @return the updated task if found, or null if not
     * @throws IllegalArgumentException if validation fails
     */
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isEmpty()) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        Task existingTask = taskOptional.get();
        // Preserve original creation date
        updatedTask.setCreatedAt(existingTask.getCreatedAt());
        updatedTask.setUpdatedAt(now);
        // Validate all fields for update
        validateTask(updatedTask, false);
        // Apply updates
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setUpdatedAt(now);
        existingTask.setDueDateTime(updatedTask.getDueDateTime());
        return taskRepository.save(existingTask);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * Retrieves tasks by their statuses.
     *
     * @param statuses the list of statuses to filter by
     * @return a list of tasks matching the given statuses
     */
    public List<Task> getTasksByStatuses(List<String> statuses) {
        return taskRepository.findByStatusIn(statuses);
    }

    private void validateTask(Task task, boolean isCreate) {
        LocalDateTime now = LocalDateTime.of(2025, 7, 28, 0, 0);
        validateTitle(task.getTitle(), isCreate);
        validateDescription(task.getDescription());
        validateStatus(task.getStatus());
        validateDueDate(task.getDueDateTime(), task.getCreatedAt(), now, isCreate);
        validateUpdatedDate(task.getUpdatedAt(), task.getCreatedAt());
    }

    private void validateTitle(String title, boolean isCreate) {
        if (title == null || title.trim().isEmpty() || title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Title is required and must be at most " + MAX_TITLE_LENGTH + " characters.");
        }
        if (isCreate && taskRepository.findAll().stream().anyMatch(t -> t.getTitle().equalsIgnoreCase(title))) {
            throw new IllegalArgumentException("Title must be unique.");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty() || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Description is required and must be at most " + MAX_DESCRIPTION_LENGTH + " characters.");
        }
    }

    private void validateStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Status must be provided and valid.");
        }
    }

    private void validateDueDate(LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime now, boolean isCreate) {
        if (dueDate != null) {
            if (isCreate && dueDate.isBefore(now)) {
                throw new IllegalArgumentException("Due date must not be in the past.");
            }
            if (createdAt != null && dueDate.isBefore(createdAt)) {
                throw new IllegalArgumentException("Due date must be after creation date.");
            }
            if (dueDate.isAfter(now.plusYears(1))) {
                throw new IllegalArgumentException("Due date must be within 1 year from now.");
            }
        }
    }

    private void validateUpdatedDate(LocalDateTime updatedAt, LocalDateTime createdAt) {
        if (updatedAt != null && createdAt != null && updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated date must be after or equal to created date.");
        }
    }
}
