package assist;

import data.Subject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;

public interface AlertModule {
    void alertRegisterAdmin(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException;
    void alertRegisterUser(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException;
    void alertRegisterAdmin(String username, TelegramLongPollingBot bot, Long chatId) throws TelegramApiException, UnsupportedEncodingException;
    void alertQueueUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException;
    void alertSkippedUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException;
    void alertRejectUser(String username, TelegramLongPollingBot bot) throws UnsupportedEncodingException, TelegramApiException;
}
