package commands;

import assist.AlertModule;
import data.QueueDBManager;
import data.UsersDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class NextCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public NextCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            return "Следующий на очереди: " + queueDBManager.nextStudent(username, bot);
        } catch (Exception e) {
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
