package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.InvalidSubGroupException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SubjectTask implements Task{

    private final UserData userData;

    public SubjectTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule,
                          TelegramLongPollingBot bot, UsersDB usersDB,  long chat_id, QueueDBManager manager) throws InvalidSubGroupException {
        int subGroup;
        try {
            subGroup = Integer.parseInt(argument);
        } catch (Exception e) {
            throw new InvalidSubGroupException();
        }
        if(subGroup > 2 || subGroup < 1)
            throw new InvalidSubGroupException();
        userData.setSubGroup(Integer.parseInt(argument));
        return "Введите, пожалуйста название предмета, который вы ведёте(на английском языке):";
    }

    @Override
    public Task next() {
        return new SubmitTask(userData);
    }

    @Override
    public String toString() {
        return "subject";
    }
}
