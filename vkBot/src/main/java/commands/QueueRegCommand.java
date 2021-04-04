package commands;

import data.QueueDBManager;
import data.Subject;
import data.UsersDB;

import java.sql.SQLException;

public class QueueRegCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueRegCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(String username, String subject) {
        try {
            manager.add(username, Subject.forName(subject));
            return "Вы успешно зарегистрировались в очередь.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Что-то пошло сильно не так. Напишите @true47iq пж.";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Проверьте, является ли название предмета одним из {OPD, Programming}.";
        }
    }
}
