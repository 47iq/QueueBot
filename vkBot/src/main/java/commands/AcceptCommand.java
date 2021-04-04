package commands;

import assist.RegistrationAlertModule;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class AcceptCommand implements AdminCommand{

    private final UsersDB usersDB;

    private final WaitingPoolDB waitingPoolDB;

    private final RegistrationAlertModule alertModule;

    public AcceptCommand(UsersDB usersDB, WaitingPoolDB waitingPoolDB, RegistrationAlertModule alertModule) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
    }

    @Override
    public String execute(String username, TelegramLongPollingBot bot) throws SQLException, TelegramApiException {
        UserData data = waitingPoolDB.getData(username);
        usersDB.register(username, data.getName(), data.getSurname(), data.getRole(), data.getGroup(),
                data.getSubGroup(), data.getSubject() == null ? null : data.getSubject().toString() , data.getChat_id());
        waitingPoolDB.delete(username);
        alertModule.alertUser(username, bot);
        return "Все ок, зарегал.";
    }
}
