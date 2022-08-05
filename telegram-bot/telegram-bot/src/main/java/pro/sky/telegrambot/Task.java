package pro.sky.telegrambot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "notification_task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idChat;
    private String taskText;
    private LocalDateTime taskDate;

    // public Task(Long idChat, String taskText, LocalDateTime taskDate) {
    //    this.idChat = idChat;
    //    this.taskText = taskText;
   //     this.taskDate = taskDate;
  //  }

    public Task() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public LocalDateTime getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(LocalDateTime taskDate) {
        this.taskDate = taskDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(idChat, task.idChat) && Objects.equals(taskText, task.taskText) && Objects.equals(taskDate, task.taskDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChat, taskText, taskDate);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", idChat=" + idChat +
                ", taskText='" + taskText + '\'' +
                ", taskDate=" + taskDate +
                '}';
    }
}
