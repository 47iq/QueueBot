package commands;

import assist.AlertModule;
import assist.TaskManager;
import data.WaitingPoolDB;
import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RegisterCommand implements AuthCommand{

    WaitingPoolDB usersDB;
    AlertModule alertModule;

    public RegisterCommand(WaitingPoolDB db, AlertModule alertModule) {
        this.usersDB = db;
        this.alertModule = alertModule;
    }

    @Override
    public SendMessage execute(String username, TaskManager taskManager, long chat_id) throws FatalError {
        return taskManager.startRegister(username, chat_id);
    }
}
