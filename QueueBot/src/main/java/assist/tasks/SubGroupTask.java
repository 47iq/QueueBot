package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.InvalidGroupException;
import exceptions.InvalidRoleException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.regex.Pattern;

public class SubGroupTask implements Task{

    private final UserData userData;

    public SubGroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB, long chat_id, QueueDBManager manager) throws InvalidGroupException {
        if(Pattern.matches("^[a-zA-Z][1-9][0-9]{3}$", argument)) {
            userData.setGroup(argument.toLowerCase());
            return "Введите, пожалуйста номер вашей подгруппы:";
        } else {
            throw new InvalidGroupException();
        }
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
