package commands;

import assist.AlertModule;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class RegisterCommand implements AuthCommand{

    WaitingPoolDB usersDB;
    AlertModule alertModule;

    public RegisterCommand(WaitingPoolDB db, AlertModule alertModule) {
        this.usersDB = db;
        this.alertModule = alertModule;
    }

    @Override
    public String execute(TelegramLongPollingBot bot, String username, String name, String surname, String role, int group, int subGroup, String subject, long chat_id) {
        try {
            usersDB.register(username, name, surname, role, group, subGroup, subject, chat_id);
            alertModule.alertRegisterAdmin(username, bot);
            return "Запрос передан на модерацию.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой... Что-то пошло не так.";
        }
    }
}
