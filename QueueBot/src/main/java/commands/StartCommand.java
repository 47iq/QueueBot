package commands;

import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StartCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public StartCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot) {
        SendMessage sendMessage = new SendMessage();
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = queueDBManager.startQueue(username);
            if(message != null)
                message = charset.decode(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))).toString();
            else {
                sendMessage.setText("Никто не пришел на фан-встречу((( Очередь пустая.");
                return sendMessage;
            }
            sendMessage.setText("Здравствуйте! Первый сегодня: " + message);
            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage.setText("Ой. Что-то пошло сильно не так. Напишите пж @true_47iq");
            return sendMessage;
        }
    }
}
