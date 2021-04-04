package data;

import assist.AlertModule;
import assist.ObjectFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManagerImpl implements DBManager{

    private WaitingPoolDB waitingPoolDB;

    private UsersDB usersDB;

    private QueueDBManager queueDBManager;

    private TablesDBManager tablesDBManager;

    private Connection connection;

    private final ObjectFactory factory;

    public DBManagerImpl(ObjectFactory factory) {
        this.factory = factory;
    }

    @Override
    public void start(String url, String user, String password, AlertModule alertModule) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            tablesDBManager = factory.getTablesDB(connection, factory);
            waitingPoolDB = factory.getWaitingPool(connection, factory);
            usersDB = factory.getUsersDB(connection, factory);
            queueDBManager = factory.getQueueData(connection, usersDB, tablesDBManager, alertModule);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public WaitingPoolDB getWaitingPool() {
        return waitingPoolDB;
    }

    @Override
    public UsersDB getUsersDB() {
        return usersDB;
    }

    @Override
    public QueueDBManager getQueueDB() {
        return queueDBManager;
    }
}
