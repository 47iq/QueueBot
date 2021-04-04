package commands;

import assist.AlertModule;
import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SkipCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public SkipCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            return "Предыдущий студент скипнут. Следующий: " + queueDBManager.skipStudent(username, bot);
        } catch (Exception e) {
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
