package commands;

import data.QueueDBManager;
import data.Subject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;

public class QueueRegCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueRegCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public SendMessage execute(String username, String subject) {
        SendMessage sendMessage = new SendMessage();
        try {
            manager.add(username, Subject.forName(subject));
            sendMessage.setText("Вы успешно записались в очередь. Посмотреть ее можно командой /getqueue");
            return sendMessage;
        } catch (SQLException e) {
            sendMessage.setText("Ой. Что-то пошло не так. Возможно, вы уже записаны в очередь. Если нет, то напишите пж @true_47iq");
            return sendMessage;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            sendMessage.setText("Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.");
            return sendMessage;
        }
    }
}
