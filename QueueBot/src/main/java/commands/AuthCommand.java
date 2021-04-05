package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AuthCommand extends Command{
    SendMessage execute(TelegramLongPollingBot bot, String username, String name, String surname, String role, int group, int subGroup, String subject, long chat_id);
}
