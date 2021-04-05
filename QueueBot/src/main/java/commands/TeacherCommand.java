package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TeacherCommand extends Command{
    SendMessage execute(String username, TelegramLongPollingBot bot);
}
