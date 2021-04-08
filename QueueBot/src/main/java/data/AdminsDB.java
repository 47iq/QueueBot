package data;

import java.sql.SQLException;

public interface AdminsDB {
    void add(String username, String group) throws SQLException;
    boolean contains(String username);
    String getAdminUsername(String group);
}
