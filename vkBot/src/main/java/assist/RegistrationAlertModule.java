package assist;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;

public interface RegistrationAlertModule {
    void alertAdmin(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException;
    void alertUser(String username, TelegramLongPollingBot bot) throws TelegramApiException;
}
