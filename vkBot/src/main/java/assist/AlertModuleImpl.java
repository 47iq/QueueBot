package assist;

import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AlertModuleImpl implements AlertModule {

    private final UsersDB usersDB;
    private final WaitingPoolDB waitingPoolDB;

    public AlertModuleImpl(UsersDB usersDB, WaitingPoolDB waitingPoolDB) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
    }

    @Override
    public void alertRegisterAdmin(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(usersDB.getChatId(System.getenv("ADMIN_USERNAME"))));
        String text = "Привет, там " + username + " хочет зарегаться. Его данные: " + waitingPoolDB.getInfo(username);
        text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertRegisterUser(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        String text = "Ура. Ваша заявка одобрена.";
        text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
        message.setChatId(waitingPoolDB.getChatId(username));
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertQueueUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        String text = "Ваша очередь на сдачу " + subject.toString() + ". Удачи!";
        text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
        message.setChatId(waitingPoolDB.getChatId(username));
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertSkippedUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        String text = "Вас скипнули в очереди на сдачу " + subject.toString() + ":(";
        text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
        message.setChatId(waitingPoolDB.getChatId(username));
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertRejectUser(String username, TelegramLongPollingBot bot) throws UnsupportedEncodingException, TelegramApiException {
        SendMessage message = new SendMessage();
        String text = "К сожалению ваша заявка отклонена. Проверьте данные и попробуйте еще раз.";
        text = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(text.getBytes("windows-1251"))).toString();
        message.setChatId(waitingPoolDB.getChatId(username));
        message.setText(text);
        bot.execute(message);
    }
}
