package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GroupTask implements Task{

    private final UserData userData;

    public GroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) {
        userData.setRole(argument);
        return "Введите, пожалуйста номер вашей группы(или выберите одну из зарегистрированных):";
    }

    @Override
    public Task next() {
        return new SubGroupTask(userData);
    }

    @Override
    public String toString() {
        return "group";
    }
}
