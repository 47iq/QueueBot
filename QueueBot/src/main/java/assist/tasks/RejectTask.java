package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class RejectTask implements Task{

    private final String message;

    public RejectTask(String message) {
        this.message = message;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) throws FatalError {
        try {
            alertModule.alertRejectUser(argument, bot);
            waitingPoolDB.delete(argument);
            return "F челу";
        } catch (Exception e) {
            e.printStackTrace();
            throw new FatalError();
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
