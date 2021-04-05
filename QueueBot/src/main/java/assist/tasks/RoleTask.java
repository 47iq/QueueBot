package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class RoleTask implements Task{

    private final UserData userData;

    public RoleTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        message += " role";
        return "Введите, пожалуйста запрашиваемую роль{admin, teacher, student}:";
    }

    @Override
    public Task next() {
        return null;
    }
}
