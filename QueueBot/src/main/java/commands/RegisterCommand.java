package commands;

import assist.AlertModule;
import data.WaitingPoolDB;
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
    public SendMessage execute(TelegramLongPollingBot bot, String username, String name, String surname, String role, int group, int subGroup, String subject, long chat_id) {
        SendMessage sendMessage = new SendMessage();
        try {
            usersDB.register(username, name, surname, role, group, subGroup, subject, chat_id);
            alertModule.alertRegisterAdmin(username, bot);
            sendMessage.setText("Запрос передан на модерацию.");
            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage.setText("Ой... Что-то пошло не так.");
            return sendMessage;
        }
    }
}
