package commands;

import assist.TaskManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface QueueCommand extends Command{
    SendMessage execute(String username, TaskManager taskManager, long chat_id);
}
