package uk.gov.hmcts.reform.dev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.models.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface  TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByStatusIn(List<String> statuses);
}
