package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public interface AdminCommand extends Command{
    String execute(String username, TelegramLongPollingBot bot) throws SQLException, TelegramApiException;
}
