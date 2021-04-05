package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface QueueCommand extends Command{
    SendMessage execute(String username, String subject);
}
