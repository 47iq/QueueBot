package commands;

import assist.TaskManager;
import data.QueueDBManager;
import data.Subject;
import exceptions.FatalError;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.SQLException;

public class QueueRegCommand implements QueueCommand{

    private final QueueDBManager manager;

    public QueueRegCommand(QueueDBManager manager) {
        this.manager = manager;
    }

    @Override
    public SendMessage execute(String username, TaskManager taskManager, long chat_id) throws FatalError {
        return  taskManager.startQueue(username, chat_id);
    }
}
