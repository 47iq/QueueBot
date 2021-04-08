package commands;

import assist.TaskManager;
import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AuthCommand extends Command{
    SendMessage execute(String username, TaskManager taskManager, long chat_id) throws FatalError;
}
