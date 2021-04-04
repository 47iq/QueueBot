package data;

public interface DBManager {
    void start(String url, String user, String password);
    WaitingPoolDB getWaitingPool();
    UsersDB getUsersDB();
    QueueDBManager getQueueDB();
}
