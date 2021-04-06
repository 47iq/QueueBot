package inlinekeyboard;

import data.UsersDB;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class KeyboardCreatorImpl implements KeyboardCreator{

    UsersDB usersDB;

    private final AdminInlineKeyboardCreator adminInlineKeyboardCreator;

    private final TeacherInlineKeyboardCreator teacherInlineKeyboardCreator;

    private final StudentInlineKeyboardCreator studentInlineKeyboardCreator;

    public KeyboardCreatorImpl(UsersDB usersDB) {
        this.usersDB = usersDB;
        adminInlineKeyboardCreator = new AdminInlineKeyboardCreator();
        teacherInlineKeyboardCreator = new TeacherInlineKeyboardCreator();
        studentInlineKeyboardCreator = new StudentInlineKeyboardCreator();
    }

    @Override
    public InlineKeyboardMarkup getInlineKeyboardMarkup(String username) {
        String role = usersDB.getRole(username);
        switch (role) {
            case "admin" -> {
                return adminInlineKeyboardCreator.createInlineKeyBoardMarkUp();
            }
            case "teacher" -> {
                return teacherInlineKeyboardCreator.createInlineKeyBoardMarkUp();
            }
            case "student" -> {
                return studentInlineKeyboardCreator.createInlineKeyBoardMarkUp();
            }
            default -> {
                return null;
            }
        }
    }
}
