package commands;

import data.QueueDBManager;
import data.Subject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;

public class QueueLeaveCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueLeaveCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public SendMessage execute(String username, String subject) {
        SendMessage sendMessage = new SendMessage();
        try {
            manager.remove(username, Subject.forName(subject));
            sendMessage.setText("Вы успешно записались в очередь. Посмотреть ее можно командой /getqueue");
            return sendMessage;
        } catch (SQLException e) {
            sendMessage.setText("Что-то пошло не так");
            return sendMessage;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendMessage.setText("Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.");
            return sendMessage;
        }
    }
}
