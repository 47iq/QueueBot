package commands;

import data.QueueDBManager;
import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class FinishCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public FinishCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot) throws FatalError {
        SendMessage sendMessage = null;
        try {
            sendMessage = new SendMessage();
            queueDBManager.finishQueue(username);
            sendMessage.setText("Сдача завершена. До свидания и хорошего вам дня!");
            return sendMessage;
        } catch (Exception e) {
            throw new FatalError();
        }
    }
}
