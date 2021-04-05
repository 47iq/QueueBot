package commands;

import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NextCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public NextCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage();
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = queueDBManager.nextStudent(username, bot);
            if(message != null)
                message = charset.decode(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))).toString();
            else {
                sendMessage.setText("Ура, вы всех завалили! Очередь пустая.");
                return sendMessage;
            }
            sendMessage.setText("Следующий на очереди: " + message);
            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage.setText("Ой. Что-то пошло сильно не так. Напишите пж @true_47iq");
            return sendMessage;
        }
    }
}
