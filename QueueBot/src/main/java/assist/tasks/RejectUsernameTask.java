package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class RejectUsernameTask implements Task{

    private final String message;

    public RejectUsernameTask(String message) {
        this.message = message;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) {
        return "Кого вы хотите отклонить?";
    }

    @Override
    public Task next() {
        return new RejectTask(message);
    }

    @Override
    public String toString() {
        return "rejectusername";
    }
}
