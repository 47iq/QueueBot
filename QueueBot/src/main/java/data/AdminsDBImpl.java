package data;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AdminsDBImpl implements AdminsDB{

    private final Connection connection;

    private final Map<Integer, String> admins = new HashMap<>();

    public AdminsDBImpl(Connection connection) throws SQLException {
        this.connection = connection;
        create();
        init();
    }

    private void init() throws SQLException {
        String sql = "SELECT * FROM admins";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int group = resultSet.getInt(1);
            String admin = resultSet.getString(2);
            admins.put(group, admin);
        }
    }

    private void create() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS admins " +
                "(study_group int primary key, admin TEXT not null)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public boolean contains(String username) {
        return admins.containsValue(username);
    }

    @Override
    public String getAdminUsername(int group) {
        return admins.get(group);
    }

    @Override
    public void add(String username, int group) throws SQLException {
        String sql = "INSERT INTO admins VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, group);
        preparedStatement.setString(2, username);
        preparedStatement.execute();
        admins.put(group, username);
    }
}
