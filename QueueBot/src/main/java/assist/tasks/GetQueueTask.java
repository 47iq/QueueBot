package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class GetQueueTask implements Task{

    private String subject;

    public GetQueueTask(String subject) {
        this.subject = subject;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB,
                          AlertModule alertModule, TelegramLongPollingBot bot,
                          UsersDB usersDB, long chat_id, QueueDBManager manager) {
        subject = argument;
        try {
            List<String> list = manager.getQueue(Subject.forName(subject), username);
            String result = list.stream().map(usersDB::getName).reduce((x, y)->(x + "\n" + y)).get();
            Charset charset = Charset.forName("windows-1251");
            return charset.decode(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8))).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return ("Ой. Что-то пошло сильно не так. Напишите пж @true_47iq");
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "getqueue";
    }
}
