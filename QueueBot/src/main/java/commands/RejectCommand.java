package commands;

import assist.AlertModule;
import assist.TaskManager;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class RejectCommand implements AdminCommand{

    private final UsersDB usersDB;

    private final WaitingPoolDB waitingPoolDB;

    private final AlertModule alertModule;

    public RejectCommand(UsersDB usersDB, WaitingPoolDB waitingPoolDB, AlertModule alertModule) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot, TaskManager taskManager, long chat_id) throws SQLException, TelegramApiException, UnsupportedEncodingException {
        return taskManager.startReject(username, chat_id);
    }
}
