package commands;

import assist.AlertModule;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class AcceptCommand implements AdminCommand{

    private final UsersDB usersDB;

    private final WaitingPoolDB waitingPoolDB;

    private final AlertModule alertModule;

    public AcceptCommand(UsersDB usersDB, WaitingPoolDB waitingPoolDB, AlertModule alertModule) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) throws SQLException, TelegramApiException, UnsupportedEncodingException {
        UserData data = waitingPoolDB.getData(username);
        usersDB.register(username, data.getName(), data.getSurname(), data.getRole(), data.getGroup(),
                data.getSubGroup(), data.getRole().equals("teacher") ? data.getSubject().toString() : null , data.getChat_id());
        waitingPoolDB.delete(username);
        alertModule.alertRegisterUser(username, bot);
        return "Все ок, зарегал.";
    }
}
