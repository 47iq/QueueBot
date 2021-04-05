package commands;

import assist.TaskManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AuthCommand extends Command{
    SendMessage execute(String username, TaskManager taskManager);
}
