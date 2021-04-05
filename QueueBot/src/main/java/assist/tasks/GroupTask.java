package assist.tasks;

import assist.AlertModule;
import data.UserData;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class GroupTask implements Task{

    private final UserData userData;

    public GroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule, TelegramLongPollingBot bot) {
        userData.setRole(argument);
        return "Введите, пожалуйста номер вашей группы:";
    }

    @Override
    public Task next() {
        return new SubGroupTask(userData);
    }
}
