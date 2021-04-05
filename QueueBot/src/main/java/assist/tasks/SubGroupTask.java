package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class SubGroupTask implements Task{

    private final UserData userData;

    public SubGroupTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        return "Введите, пожалуйста номер вашей подгруппы:";
    }

    @Override
    public Task next() {
        return null;
    }
}
