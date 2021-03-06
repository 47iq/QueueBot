package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class NameTask implements Task{

    private final UserData userData;

    public NameTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) {
        userData.setSurname(argument);
        return "Введите, пожалуйста ваше имя:";
    }

    @Override
    public Task next() {
        return new RoleTask(userData);
    }

    @Override
    public String toString() {
        return "name";
    }
}
