package assist.tasks;

import data.UserData;
import data.WaitingPoolDB;

public class SubjectTask implements Task{

    private final UserData userData;

    public SubjectTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB) {
        return null;
    }

    @Override
    public Task next() {
        return null;
    }
}
