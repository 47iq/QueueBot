package data;

import java.sql.SQLException;

public interface UsersDB {
    void register(String username, String name, String surname, String role, int group, int subgroup,
                  String subject, long Chat_id) throws SQLException;
    boolean isPresent(String username);
    boolean isTeacher(String username);
    boolean isAdmin(String username);
    int getGroup(String username);
    int getSubGroup(String username);
    String getName(String username);
    Subject getSubject(String username);
    long getChatId(String username);
    Long getAdminChatId(int group);
}
