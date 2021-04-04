package commands;

import data.QueueDBManager;
import data.Subject;

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
            return "Вы успешно записались в очередь. Посмотреть ее можно командой /getqueue";
        } catch (SQLException e) {
            return "Ой. Что-то пошло не так. Возможно, вы уже записаны в очередь. Если нет, то напишите пж @true_47iq";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.";
        }
    }
}
