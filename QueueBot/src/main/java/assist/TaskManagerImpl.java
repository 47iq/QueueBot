package assist;

import assist.tasks.Task;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class TaskManagerImpl implements TaskManager{

    private Map<String, Task> taskMap;

    private final WaitingPoolDB waitingPoolDB;

    private final AlertModule alertModule;

    public TaskManagerImpl(WaitingPoolDB waitingPoolDB, AlertModule alertModule) {
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
    }

    @Override
    public boolean hasRunningTasks(String username) {
        return taskMap.containsKey(username);
    }

    @Override
    public void clearTasks(String username) {
        taskMap.remove(username);
    }

    @Override
    public SendMessage executeNextTask(String username, String arg, TelegramLongPollingBot bot) {
        Task task = taskMap.get(username);
        String ans = task.execute(username, arg, waitingPoolDB, alertModule, bot);
        SendMessage message = new SendMessage();
        message.setText(ans);
        taskMap.put(username, task.next());
        return message;
    }
}
