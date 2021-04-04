package commands;

import assist.AlertModule;
import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class StartCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public StartCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            return "Здравствуйте! Первый сегодня: " + queueDBManager.startQueue(username);
        } catch (Exception e) {
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
