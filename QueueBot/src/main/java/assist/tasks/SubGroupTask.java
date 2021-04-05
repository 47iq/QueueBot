package assist.tasks;

import assist.AlertModule;
import data.UserData;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SubGroupTask implements Task{

    private final UserData userData;

    public SubGroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule, TelegramLongPollingBot bot) {
        userData.setGroup(Integer.parseInt(argument));
        return "Введите, пожалуйста номер вашей подгруппы:";
    }

    @Override
    public Task next() {
        if(userData.getRole().equals("teacher"))
            return new SubjectTask(userData);
        else
            return new SubmitTask(userData);
    }

    @Override
    public String toString() {
        return "subgroup";
    }
}
