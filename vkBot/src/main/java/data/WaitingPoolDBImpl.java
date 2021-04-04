package data;

import assist.ObjectFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class WaitingPoolDBImpl implements WaitingPoolDB{

    private final Connection connection;

    private final Map<String, UserData> users = new HashMap<>();

    private final ObjectFactory factory;

    public WaitingPoolDBImpl(Connection connection, ObjectFactory factory) throws SQLException {
        this.connection = connection;
        this.factory = factory;
        create();
        init();
    }

    private void init() throws SQLException {
        String sql = "SELECT * FROM waiting_pool";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String username = resultSet.getString(1);
            String name = resultSet.getString(2);
            String surname = resultSet.getString(3);
            String role = resultSet.getString(4);
            int group = resultSet.getInt(5);
            int subGroup = resultSet.getInt(6);
            String subject = resultSet.getString(7);
            long chat_id = resultSet.getLong(8);
            addToCache(username, name, surname, role, group, subGroup, subject, chat_id);
        }
    }

    private void create() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS  waiting_pool " +
                "(username TEXT primary key, name TEXT not null , surname TEXT not null, role varchar not null, " +
                "studyGroup int not null, subGroup int not null, subject varchar, chat_id bigint)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public void register(String username, String name, String surname, String role, int studyGroup,
                         int subGroup, String subject, long chat_id) throws SQLException {
        String sql = "INSERT INTO waiting_pool (username, name, surname, role, studyGroup, subGroup, subject, chat_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, surname);
        preparedStatement.setString(4, role);
        preparedStatement.setInt(5, studyGroup);
        preparedStatement.setInt(6, subGroup);
        if(subject != null)
            preparedStatement.setString(7, subject);
        else
            preparedStatement.setNull(7, Types.VARCHAR);
        preparedStatement.setLong(8, chat_id);
        System.out.println(preparedStatement);
        preparedStatement.execute();
        addToCache(username, name, surname, role, studyGroup, subGroup, subject, chat_id);
    }

    @Override
    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM waiting_pool where username = (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.execute();
    }

    private void addToCache(String username, String name, String surname, String role, int studyGroup,
                            int subGroup, String subject, long chat_id) {
        users.put(username, factory.getUsersData(name, surname, role, studyGroup, subGroup, subject, chat_id));
    }

    @Override
    public String getInfo(String username) {
        UserData data = users.get(username);
        return data.getName() + " " + data.getSurname() + " " + data.getGroup() + " " + data.getSubGroup() + " " + data.getRole() + " " + data.getSubject();
    }

    @Override
    public UserData getData(String username) {
        return users.get(username);
    }

    @Override
    public String getChatId(String username) {
        return String.valueOf(users.get(username).getChat_id());
    }
}
