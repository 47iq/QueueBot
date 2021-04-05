package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class GroupTask implements Task{

    private final UserData userData;

    public GroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        return "Введите, пожалуйста номер вашей группы:";
    }

    @Override
    public Task next() {
        return null;
    }
}
