package assist;

import data.QueueDBManager;
import data.UserData;
import data.UsersDB;
import data.WaitingPoolDB;

import java.sql.Connection;
import java.sql.SQLException;

public interface ObjectFactory {
    UsersDB getUsersDB(Connection connection, ObjectFactory factory) throws SQLException;

    WaitingPoolDB getWaitingPool(Connection connection, ObjectFactory factory) throws SQLException;

    QueueDBManager getQueueData(Connection connection, UsersDB users);

    UserData getUsersData(String name, String surname, String role, int studyGroup, int subGroup, String subject, long chat_id);
}
