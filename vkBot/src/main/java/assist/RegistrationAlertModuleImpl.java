package assist;

import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RegistrationAlertModuleImpl implements RegistrationAlertModule{

    private final UsersDB usersDB;
    private final WaitingPoolDB waitingPoolDB;

    public RegistrationAlertModuleImpl(UsersDB usersDB, WaitingPoolDB waitingPoolDB) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
    }

    @Override
    public void alertAdmin(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(usersDB.getChatId(System.getenv("ADMIN_USERNAME"))));
        String text = "Привет, там " + username + " хочет зарегаться. Его данные: " + StandardCharsets.UTF_8.decode(ByteBuffer.wrap(waitingPoolDB.getInfo(username).getBytes("windows-1251"))).toString();
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertUser(String username, TelegramLongPollingBot bot) throws TelegramApiException {
        SendMessage message = new SendMessage();
        String text = "Ура. Ваша регистрация одобрена.";
        message.setChatId(waitingPoolDB.getChatId(username));
        message.setText(text);
        bot.execute(message);
    }
}
