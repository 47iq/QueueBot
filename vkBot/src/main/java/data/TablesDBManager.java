package data;

import java.sql.SQLException;

public interface TablesDBManager {
    void add(String tableName) throws SQLException;
    boolean contains(String tableName);
}
