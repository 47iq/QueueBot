package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SurnameTask implements Task{

    private final UserData userData;

    public SurnameTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB,  long chat_id, QueueDBManager manager) {
        return "Введите, пожалуйста вашу фамилию:";
    }

    @Override
    public Task next() {
        return new NameTask(userData);
    }

    @Override
    public String toString() {
        return "surname";
    }
}
