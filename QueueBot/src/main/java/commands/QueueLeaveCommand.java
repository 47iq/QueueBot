package commands;

import assist.TaskManager;
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
    public SendMessage execute(String username, TaskManager taskManager, long chat_id) {
        return  taskManager.startLeave(username, chat_id);
    }
}
