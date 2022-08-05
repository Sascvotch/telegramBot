package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
//String createTask(Integer chatId, String taskText, LocalDateTime taskDate);
@Query(value = "SELECT * FROM notification_task where task_date=:nowTime", nativeQuery = true)
List<Task>  getTasksByTaskDateLikeNow (@Param("nowTime")LocalDateTime nowTime);
}
