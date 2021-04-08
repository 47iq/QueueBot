package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.FatalError;
import exceptions.InvalidGroupException;
import exceptions.InvalidRoleException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface Task {
    String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                   TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) throws FatalError, InvalidRoleException, InvalidGroupException;
    Task next();
}
