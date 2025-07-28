package uk.gov.hmcts.reform.dev.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.services.TaskService;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduler for updating overdue tasks.
 * Runs daily at 1:00 AM and sets status to "Overdue" for tasks
 * with status "Pending" or "In Progress" whose due date has passed.
 */
@Component
@RequiredArgsConstructor
public class TaskStatusScheduler {
    private final TaskService taskService;

    /**
     * Updates the status of tasks to "Overdue" if their due date has passed.
     * Only tasks with status PENDING or IN_PROGRESS are considered.
     * Runs automatically every day at 1:00 AM.
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void updateOverdueTasks() {
        List<String> statuses = List.of("Pending", "In Progress");
        List<Task> tasks = taskService.getTasksByStatuses(statuses);
        LocalDateTime now = LocalDateTime.now();
        for (Task task : tasks) {
            if (task.getDueDateTime() != null && task.getDueDateTime().isBefore(now)) {
                task.setStatus("Overdue");
                taskService.updateTask(task.getId(), task);
            }
        }
    }
}
