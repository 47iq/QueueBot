package assist;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TaskManager {
    boolean hasRunningTasks(String username);
    void clearTasks(String username);
    SendMessage executeNextTask(String username, String arg, TelegramLongPollingBot bot);
    SendMessage startRegister(String username);
    SendMessage startAccept(String username);
    SendMessage startReject(String username);
}
