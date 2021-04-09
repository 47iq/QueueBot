package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.FatalError;
import inlinekeyboard.InlineKeyboardCreator;
import inlinekeyboard.KeyboardCreator;
import inlinekeyboard.KeyboardCreatorImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

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
                          UsersDB usersDB, long chat_id, QueueDBManager manager) throws FatalError {
        subject = argument;
        try {
            List<String> list = manager.getQueue(Subject.forName(subject), username);
            if(list.isEmpty())
                return "Очередь пуста.";
            String result = list.stream().map(usersDB::getName).reduce((x, y)->(x + "\n" + y)).get();
            Charset charset = Charset.forName("windows-1251");
            String chars =  charset.decode(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8))).toString();
            String letterI = "I";
            chars = chars.replace("И", letterI);
            return chars;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FatalError();
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
