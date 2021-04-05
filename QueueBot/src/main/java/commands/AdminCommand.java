package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface AdminCommand extends Command{
    SendMessage execute(String username, TelegramLongPollingBot bot) throws SQLException, TelegramApiException, UnsupportedEncodingException;
}
