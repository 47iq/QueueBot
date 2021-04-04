package data;

import java.sql.SQLException;

public interface WaitingPoolDB {
    void register(String username, String name, String surname, String role, int group,
                  int subGroup, String subject, long chat_id) throws SQLException;
    String getInfo(String username);
    UserData getData(String username);
    void delete(String username) throws SQLException;
    String getChatId(String username);
}
