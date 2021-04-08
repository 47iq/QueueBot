package assist;

import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TaskManager {
    boolean hasRunningTasks(String username);
    void clearTasks(String username);
    SendMessage executeNextTask(String username, String arg, TelegramLongPollingBot bot, long chat_id) throws FatalError;
    SendMessage startRegister(String username, long chat_id) throws FatalError;
    SendMessage startAccept(String username, long chat_id) throws FatalError;
    SendMessage startReject(String username, long chat_id) throws FatalError;
    SendMessage startLeave(String username, long chat_id) throws FatalError;
    SendMessage startQueue(String username, long chat_id) throws FatalError;
    SendMessage startGetQueue(String username, long chat_id) throws FatalError;
}
