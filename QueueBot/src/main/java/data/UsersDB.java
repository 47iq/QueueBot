package data;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.sql.SQLException;
import java.util.List;

public interface UsersDB {
    void register(String username, String name, String surname, String role, String group, int subgroup,
                  String subject, long Chat_id) throws SQLException;
    boolean isPresent(String username);
    boolean isTeacher(String username);
    boolean isAdmin(String username);
    String getGroup(String username);
    int getSubGroup(String username);
    String getName(String username);
    Subject getSubject(String username);
    long getChatId(String username);
    Long getAdminChatId(String group);
    String getRole(String username);
    List<String> getGroups();
}
