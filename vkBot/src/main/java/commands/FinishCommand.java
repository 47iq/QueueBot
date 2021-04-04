package commands;

import assist.AlertModule;
import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class FinishCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public FinishCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            queueDBManager.finishQueue(username);
            return "Сдача завершена. До свидания и хорошего вам дня!";
        } catch (Exception e) {
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
