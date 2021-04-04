package data;

import assist.ObjectFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UsersDBImpl implements UsersDB{

    private final Connection connection;

    private final Map<String, UserData> users = new HashMap<>();

    private final Set<String> admins = new HashSet<>();

    private final Set<String> teachers = new HashSet<>();

    private final ObjectFactory factory;

    public UsersDBImpl(Connection connection, ObjectFactory factory) throws SQLException {
        this.connection = connection;
        this.factory = factory;
        create();
        init();
    }

    private void init() throws SQLException {
        String sql = "SELECT * FROM users";
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
        //CREATE TYPE role AS ENUM ('admin', 'teacher', 'student');
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users " +
                "(username TEXT primary key, name TEXT not null, surname TEXT not null, role varchar not null, " +
                "studyGroup int not null, subGroup int not null, subject varchar, chat_id bigint)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public void register(String username, String name, String surname, String role,
                         int studyGroup, int subGroup, String subject, long chat_id) throws SQLException {
        String sql = "INSERT INTO users (username, name, surname, role, studyGroup, subGroup, subject, chat_id) " +
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
        addToCache(username, name, surname, role, studyGroup, subGroup, subject, chat_id);
    }

    private void addToCache(String username, String name, String surname, String role, int studyGroup,
                            int subGroup, String subject, long chat_id) {
        users.put(username, factory.getUsersData(name, surname, role, studyGroup, subGroup, subject, chat_id));
        if(role.equals("admin"))
            admins.add(username);
        if(role.equals("teacher"))
            teachers.add(username);
    }

    @Override
    public boolean isPresent(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean isTeacher(String username) {
        return teachers.contains(username);
    }

    @Override
    public boolean isAdmin(String username) {
        return admins.contains(username);
    }

    @Override
    public int getGroup(String username) {
        return users.get(username).getGroup();
    }

    @Override
    public int getSubGroup(String username) {
        return users.get(username).getSubGroup();
    }

    @Override
    public String getName(String username) {
        return users.get(username).getName() + " " + users.get(username).getSurname();
    }

    @Override
    public Subject getSubject(String username) {
        return users.get(username).getSubject();
    }

    @Override
    public long getChatId(String username) {
        return users.get(username).getChat_id();
    }
}
