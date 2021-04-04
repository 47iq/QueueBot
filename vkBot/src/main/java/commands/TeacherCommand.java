package commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public interface TeacherCommand extends Command{
    String execute(String username, TelegramLongPollingBot bot);
}
