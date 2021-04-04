package data;

import assist.AlertModule;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueueDBManagerImpl implements QueueDBManager{

    private final Connection connection;

    private final UsersDB usersDB;

    private final TablesDBManager tablesDBManager;

    private final AlertModule alertModule;

    public QueueDBManagerImpl(Connection connection, UsersDB usersDB, TablesDBManager tablesDBManager, AlertModule alertModule) {
        this.connection = connection;
        this.usersDB = usersDB;
        this.tablesDBManager = tablesDBManager;
        this.alertModule = alertModule;
    }

    @Override
    public void add(String username, Subject subject) throws SQLException {
        int group = usersDB.getGroup(username);
        int subGroup = usersDB.getSubGroup(username);
        String tableName = getTableName(username, subject);
        if(!tablesDBManager.contains(tableName)) {
            create(subject, group, subGroup);
            tablesDBManager.add(tableName);
        }
        String sql = "INSERT INTO " + tableName + " (username, priority) VALUES (?, default)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    @Override
    public String nextStudent(String username, TelegramLongPollingBot bot) throws SQLException, UnsupportedEncodingException, TelegramApiException {
        String tableName = getTableName(username);
        String userNick = getNextUsername(username);
        remove(tableName, userNick);
        userNick = getNextUsername(username);
        alertModule.alertQueueUser(userNick, usersDB.getSubject(username), bot);
        return usersDB.getName(userNick);
    }

    @Override
    public String skipStudent(String username, TelegramLongPollingBot bot) throws SQLException, UnsupportedEncodingException, TelegramApiException {
        Subject subject = usersDB.getSubject(username);
        String tableName = getTableName(username);
        String userNick = getNextUsername(username);
        remove(tableName, userNick);
        alertModule.alertSkippedUser(userNick, usersDB.getSubject(username), bot);
        add(userNick, subject);
        userNick = getNextUsername(username);
        return usersDB.getName(userNick);
    }

    @Override
    public String startQueue(String username) throws SQLException {
        String userNick = getNextUsername(username);
        return usersDB.getName(userNick);
    }

    @Override
    public void finishQueue(String username) throws SQLException {
        String tableName = getTableName(username);
        String userNick = getNextUsername(username);
        remove(tableName, userNick);
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
    public void clear(Subject subject, int group, int subgroup) throws SQLException {
        String tableName = subject + String.valueOf(group) + "_" + String.valueOf(subgroup);
        String clearTableSQL = "DELETE  FROM " + tableName;
        PreparedStatement preparedStatement = connection.prepareStatement(clearTableSQL);
        preparedStatement.execute();
    }

    @Override
    public List<String> getQueue(Subject subject, String username) throws SQLException {
        String tableName = getTableName(username, subject);
        String getTable = "SELECT * FROM " + tableName;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getTable);
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    private String getNextUsername(String tableName) throws SQLException {
        String sql = "SELECT username FROM " + tableName + " WHERE priority = (SELECT MIN(priority) FROM " + tableName + ")";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();
        String userNick = null;
        while (resultSet.next()) {
            userNick = resultSet.getString(1);
        }
        return userNick;
    }

    private void remove(String tableName, String username) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE username = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    private String getTableName(String username) {
        return usersDB.getSubject(username) + String.valueOf(usersDB.getGroup(username)) + "_" + String.valueOf(usersDB.getSubGroup(username));
    }

    private String getTableName(String username, Subject subject) {
        return subject.toString() + String.valueOf(usersDB.getGroup(username)) + "_" + String.valueOf(usersDB.getSubGroup(username));
    }
}
