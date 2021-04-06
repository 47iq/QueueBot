package assist;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TaskManager {
    boolean hasRunningTasks(String username);
    void clearTasks(String username);
    SendMessage executeNextTask(String username, String arg, TelegramLongPollingBot bot, long chat_id);
    SendMessage startRegister(String username, long chat_id);
    SendMessage startAccept(String username, long chat_id);
    SendMessage startReject(String username, long chat_id);
    SendMessage startLeave(String username, long chat_id);
    SendMessage startQueue(String username, long chat_id);
    SendMessage startGetQueue(String username, long chat_id);
}
