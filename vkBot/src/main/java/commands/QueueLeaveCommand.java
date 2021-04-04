package commands;

import data.QueueDBManager;
import data.Subject;

import java.sql.SQLException;

public class QueueLeaveCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueLeaveCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public String execute(String username, String subject) {
        try {
            manager.remove(username, Subject.forName(subject));
            return "Вы успешно записались в очередь. Посмотреть ее можно командой /getqueue";
        } catch (SQLException e) {
            return "Что-то пошло не так";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.";
        }
    }
}
