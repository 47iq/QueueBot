package data;

import assist.ObjectFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TablesDBManagerImpl implements TablesDBManager{

    private final Connection connection;

    private final ObjectFactory factory;

    private final Set<String> tables = new HashSet<>();

    public TablesDBManagerImpl(Connection connection, ObjectFactory factory) throws SQLException {
        this.connection = connection;
        this.factory = factory;
        create();
        init();
    }

    private void init() throws SQLException {
        String sql = "SELECT * FROM tables";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            String table = resultSet.getString(1);
            tables.add(table);
        }
    }

    private void create() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tables " +
                "(tableName TEXT primary key, id serial)";
        PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL);
        preparedStatement.execute();
    }

    @Override
    public void add(String tableName) throws SQLException {
        String sql = "INSERT INTO tables VALUES (?, default)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, tableName);
        preparedStatement.execute();
        tables.add(tableName);
    }

    @Override
    public boolean contains(String tableName) {
        return tables.contains(tableName);
    }
}
