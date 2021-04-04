package data;

import assist.AlertModule;

public interface DBManager {
    void start(String url, String user, String password, AlertModule alertModule);
    WaitingPoolDB getWaitingPool();
    UsersDB getUsersDB();
    QueueDBManager getQueueDB();
}
