package commands;

import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class FinishCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public FinishCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot) {
        SendMessage sendMessage = null;
        try {
            sendMessage = new SendMessage();
            queueDBManager.finishQueue(username);
            sendMessage.setText("Сдача завершена. До свидания и хорошего вам дня!");
            return sendMessage;
        } catch (Exception e) {
            sendMessage.setText("Ой. Что-то пошло сильно не так. Напишите пж @true_47iq");
            return sendMessage;
        }
    }
}
