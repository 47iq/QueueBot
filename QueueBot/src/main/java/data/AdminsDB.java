package data;

import java.sql.SQLException;

public interface AdminsDB {
    void add(String username, int group) throws SQLException;
    boolean contains(String username);
    String getAdminUsername(int group);
}
