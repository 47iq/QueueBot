package commands;

import data.QueueDBManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NextCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public NextCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) {
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = queueDBManager.nextStudent(username, bot);
            if(message != null)
                message = charset.decode(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))).toString();
            else
                return "Ура, вы всех завалили! Очередь пустая.";
            return "Следующий на очереди: " + message;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой. Что-то пошло сильно не так. Напишите пж @true_47iq";
        }
    }
}
