package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface AuthCommand extends Command{
    String execute(TelegramLongPollingBot bot, String username, String name, String surname, String role, int group, int subGroup, String subject, long chat_id);
}
