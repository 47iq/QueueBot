package commands;

import assist.TaskManager;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import exceptions.FatalError;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QueueGetCommand implements QueueCommand{

    private final QueueDBManager manager;

    private final UsersDB usersDB;

    public QueueGetCommand(QueueDBManager manager, UsersDB usersDB) {
        this.manager = manager;
        this.usersDB = usersDB;
    }

    @Override
    public SendMessage execute(String username, TaskManager taskManager, long chat_id) throws FatalError {
        return  taskManager.startGetQueue(username, chat_id);
    }
}
