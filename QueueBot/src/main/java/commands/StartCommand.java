package commands;

import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StartCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public StartCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = queueDBManager.startQueue(username);
            if(message != null)
                message = charset.decode(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))).toString();
            else
                return "Никто не пришел на фан-встречу((( Очередь пустая.";
            return "Здравствуйте! Первый сегодня: " + message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
