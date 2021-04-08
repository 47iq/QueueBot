package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.InvalidRoleException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashSet;
import java.util.Set;

public class GroupTask implements Task{

    private final UserData userData;

    private static Set<String> roles = new HashSet<>(3);

    static {
        roles.add("admin");
        roles.add("student");
        roles.add("teacher");
    }

    public GroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) throws InvalidRoleException {
        if(!roles.contains(argument))
            throw new InvalidRoleException();
        userData.setRole(argument);
        return "Введите, пожалуйста вашу группу(буква перед номером должна быть на латинице):";
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
