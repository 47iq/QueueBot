package commands;

import assist.TaskManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface AdminCommand extends Command{
    SendMessage execute(String username, TelegramLongPollingBot bot, TaskManager taskManager, long chat_id) throws SQLException, TelegramApiException, UnsupportedEncodingException;
}
