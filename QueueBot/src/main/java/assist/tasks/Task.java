package assist.tasks;

import assist.AlertModule;
import data.WaitingPoolDB;

public interface Task {
    String execute(String username, String argument, WaitingPoolDB waitingPoolDB);
    Task next();
}
