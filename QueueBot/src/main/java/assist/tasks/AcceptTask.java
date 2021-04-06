package assist.tasks;

import assist.AlertModule;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class AcceptTask implements Task{

    private final String message;

    public AcceptTask(String message) {
        this.message = message;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule, TelegramLongPollingBot bot, UsersDB usersDB){
        try {
            UserData data = waitingPoolDB.getData(username);
            usersDB.register(username, data.getName(), data.getSurname(), data.getRole(), data.getGroup(),
                    data.getSubGroup(), data.getRole().equals("teacher") ? data.getSubject().toString() : null , data.getChat_id());
            waitingPoolDB.delete(username);
            alertModule.alertRegisterUser(username, bot);
            return "Все ок, зарегал";
        } catch (Exception e) {
            return "Ой... Что-то пошло не так. Возможно, пользователь уже зарегистрирован.";
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "accept";
    }
}
