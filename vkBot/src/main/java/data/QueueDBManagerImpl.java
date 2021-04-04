package data;

import java.sql.*;

public class QueueDBManagerImpl implements QueueDBManager{

    private final Connection connection;

    private final UsersDB usersDB;

    public QueueDBManagerImpl(Connection connection, UsersDB usersDB) {
        this.connection = connection;
        this.usersDB = usersDB;
    }

    @Override
    public void add(String username, Subject subject) throws SQLException {
        String tableName = subject + String.valueOf(usersDB.getGroup(username)) + "_" + String.valueOf(usersDB.getSubGroup(username));
        String sql = "INSERT INTO " + tableName + " (username, priority) VALUES (?, default)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    @Override
    public String nextStudent(String username) throws SQLException {
        String tableName = usersDB.getSubject(username) + String.valueOf(usersDB.getGroup(username)) + "_" + String.valueOf(usersDB.getSubGroup(username));
        String sql = "SELECT username FROM " + tableName + " WHERE priority = (SELECT MIN(priority) FROM " + tableName + ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        String userNick = null;
        while (resultSet.next()) {
            userNick = resultSet.getString(1);
        }
        remove(tableName, userNick);
        add(userNick, usersDB.getSubject(username));
        return userNick;
    }

    @Override
    public void createAll(int group) throws SQLException {
        create(Subject.OPD, group, 1);
        create(Subject.OPD, group, 2);
        create(Subject.PROGRAMMING, group, 1);
        create(Subject.PROGRAMMING, group, 2);
    }

    @Override
    public void create(Subject subject, int group, int subgroup) throws SQLException {
        String tableName = subject + String.valueOf(group) + "_" + String.valueOf(subgroup);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + "(username TEXT primary key, priority serial)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public void delete(Subject subject, int group, int subgroup) throws SQLException {
        String tableName = subject + String.valueOf(group) + "_" + String.valueOf(subgroup);
        String dropTableSQL = "DROP TABLE " + tableName;
        PreparedStatement preparedStatement = connection.prepareStatement(dropTableSQL);
        preparedStatement.execute();
    }

    private void remove(String tableName, String username) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE username = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }
}
