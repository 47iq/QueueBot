package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class RoleTask implements Task{

    private final UserData userData;

    public RoleTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) {
        userData.setName(argument);
        return "Введите, пожалуйста запрашиваемую роль{admin, teacher, student}:";
    }

    @Override
    public Task next() {
        return new GroupTask(userData);
    }

    @Override
    public String toString() {
        return "role";
    }
}
