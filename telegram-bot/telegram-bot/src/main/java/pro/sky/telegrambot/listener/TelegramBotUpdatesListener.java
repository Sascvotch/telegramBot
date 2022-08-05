package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.Task;
import pro.sky.telegrambot.repository.TaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final TaskRepository taskRepository;

    public TelegramBotUpdatesListener(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String str = "";
            try {
                if (update.message().text().equals("/start")) {
                    str = "Приыет";
                } else {
                    //   String str = "([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)";
                    Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
                    String date = "";
                    String item = "";
                    Matcher matcher = pattern.matcher(update.message().text());
                    if (matcher.matches()) {
                        date = matcher.group(1);
                        item = matcher.group(3);
                        str = "Добавлена заметка: " + "Дата:" + date + " Задача:" + item;
                        Task task = new Task();
                        task.setIdChat(update.message().chat().id());
                        task.setTaskText(item);
                        task.setTaskDate(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                        taskRepository.save(task);
                    } else {
                        str = "Не удалось добавить заметку";
                    }
                }
                SendMessage outMessage = new SendMessage(update.message().chat().id(), str);
                SendResponse response = telegramBot.execute(outMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<Task> nowTask = taskRepository.getTasksByTaskDateLikeNow(nowTime);
        //   System.out.println(nowTask.toString());
        for (int i = 0; i < nowTask.size(); i++) {
            SendMessage outMessage = new SendMessage(nowTask.get(i).getIdChat(), "Сейчас необходимо:" + nowTask.get(i).getTaskText());
            SendResponse response = telegramBot.execute(outMessage);
        }
    }
}
