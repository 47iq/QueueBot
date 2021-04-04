package assist;

import data.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ObjectFactoryImpl implements ObjectFactory {
    @Override
    public UsersDB getUsersDB(Connection connection, ObjectFactory factory) throws SQLException {
        return new UsersDBImpl(connection, factory);
    }

    @Override
    public WaitingPoolDB getWaitingPool(Connection connection, ObjectFactory factory) throws SQLException {
        return new WaitingPoolDBImpl(connection, factory);
    }

    @Override
    public QueueDBManager getQueueData(Connection connection, UsersDB users, TablesDBManager tablesDBManager) {
        return new QueueDBManagerImpl(connection, users, tablesDBManager);
    }

    @Override
    public UserData getUsersData(String name, String surname, String role, int studyGroup, int subGroup, String subject, long chat_id) {
        return new UserDataImpl(name, surname, role, studyGroup, subGroup, subject, chat_id);
    }

    @Override
    public TablesDBManager getTablesDB(Connection connection, ObjectFactory factory) throws SQLException {
        return new TablesDBManagerImpl(connection, factory);
    }
}
