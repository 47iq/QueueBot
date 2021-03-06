package data;

import assist.ObjectFactory;
import assist.Win1251Converter;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class UsersDBImpl implements UsersDB, Win1251Converter {

    private final Connection connection;

    private final Map<String, UserData> users = new HashMap<>();

    private final Set<String> admins = new HashSet<>();

    private final Set<String> teachers = new HashSet<>();

    private final Set<String> groups = new HashSet<>();

    private final ObjectFactory factory;

    private final AdminsDB adminsDB;

    public UsersDBImpl(Connection connection, ObjectFactory factory, AdminsDB adminsDB) throws SQLException {
        this.connection = connection;
        this.factory = factory;
        this.adminsDB = adminsDB;
        create();
        init();
        System.out.println(users);
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
            String group = resultSet.getString(5);
            int subGroup = resultSet.getInt(6);
            String subject = convert(resultSet.getString(7));
            long chat_id = resultSet.getLong(8);
            addToCache(username, name, surname, role, group, subGroup, subject, chat_id);
        }
    }

    private void create() throws SQLException {
        //CREATE TYPE role AS ENUM ('admin', 'teacher', 'student');
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users " +
                "(username TEXT primary key, name TEXT not null, surname TEXT not null, role varchar not null, " +
                "studyGroup TEXT not null, subGroup int not null, subject varchar, chat_id bigint)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public void register(String username, String name, String surname, String role,
                         String studyGroup, int subGroup, String subject, long chat_id) throws SQLException {
        String sql = "INSERT INTO users (username, name, surname, role, studyGroup, subGroup, subject, chat_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, surname);
        preparedStatement.setString(4, role);
        preparedStatement.setString(5, studyGroup);
        preparedStatement.setInt(6, subGroup);
        if (subject != null)
            preparedStatement.setString(7, subject);
        else
            preparedStatement.setNull(7, Types.VARCHAR);
        preparedStatement.setLong(8, chat_id);
        preparedStatement.execute();
        if(role.equals("admin"))
            adminsDB.add(username, studyGroup);
        addToCache(username, name, surname, role, studyGroup, subGroup, subject, chat_id);
    }

    private void addToCache(String username, String name, String surname, String role, String studyGroup,
                            int subGroup, String subject, long chat_id) throws SQLException {
        users.put(username, factory.getUsersData(name, surname, role, studyGroup, subGroup, subject, chat_id));
        if (role.equals("admin"))
            admins.add(username);
        if (role.equals("teacher"))
            teachers.add(username);
        groups.add(String.valueOf(studyGroup));
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
    public String getGroup(String username) {
        if(users.get(username) == null)
            return null;
        return users.get(username).getGroup();
    }

    @Override
    public int getSubGroup(String username) {
        return users.get(username).getSubGroup();
    }

    @Override
    public String getName(String username) {
        return users.get(username).getName() + " " + users.get(username).getSurname() + " @" + username;
    }

    @Override
    public Subject getSubject(String username) {
        return users.get(username).getSubject();
    }

    @Override
    public long getChatId(String username) {
        return users.get(username).getChat_id();
    }

    @Override
    public Long getAdminChatId(String group) {
        String username = adminsDB.getAdminUsername(group);
        if (username == null)
            return null;
        else
            return users.get(username).getChat_id();
    }

    @Override
    public String getRole(String username) {
        if(users.get(username) == null)
            return null;
        else
            return users.get(username).getRole();
    }


    @Override
    public List<String> getGroups() {
        return groups.stream().map(String::toUpperCase).collect(Collectors.toList());
    }
}
