package assist;

import data.*;

import java.sql.Connection;
import java.sql.SQLException;

public interface ObjectFactory {
    UsersDB getUsersDB(Connection connection, ObjectFactory factory, AdminsDB adminsDB) throws SQLException;

    WaitingPoolDB getWaitingPool(Connection connection, ObjectFactory factory) throws SQLException;

    QueueDBManager getQueueData(Connection connection, UsersDB users, TablesDBManager tablesDBManager);

    UserData getUsersData(String name, String surname, String role, String studyGroup, int subGroup, String subject, long chat_id);

    TablesDBManager getTablesDB(Connection connection, ObjectFactory factory) throws SQLException;

    AdminsDB getAdminsDB(Connection connection) throws SQLException;
}
