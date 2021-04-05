package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class NameTask implements Task{

    private final UserData userData;

    public NameTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        userData.
        return "Введите, пожалуйста ваше имя:";
    }

    @Override
    public Task next() {
        return new RoleTask(message);
    }
}
