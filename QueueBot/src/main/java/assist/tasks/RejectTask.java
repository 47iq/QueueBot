package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RejectTask implements Task{

    private final String message;

    public RejectTask(String message) {
        this.message = message;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager){
        try {
            waitingPoolDB.delete(argument);
            alertModule.alertRejectUser(argument, bot);
            return "F челу";
        } catch (Exception e) {
            return "Что-то пошло не так... Напишите @true47_iq";
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "reject";
    }
}
