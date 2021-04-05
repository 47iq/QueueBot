package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class SurnameTask implements Task{

    private final UserData userData;

    public SurnameTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        return "Введите, пожалуйста вашу фамилию:";
    }

    @Override
    public Task next() {
        return new NameTask(userData);
    }
}
