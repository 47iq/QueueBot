package assist;

import assist.tasks.SurnameTask;
import assist.tasks.Task;
import commands.RegisterCommand;
import data.UserDataImpl;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class TaskManagerImpl implements TaskManager{

    private final Map<String, Task> taskMap = new HashMap<>();

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
        zaloopa(message, task);
        if(task.next() != null)
            taskMap.put(username, task.next());
        else
            clearTasks(username);
        return message;
    }

    private void zaloopa(SendMessage message, Task task) {
        switch (task.toString()) {
            case "subgroup" -> {
                //add plitka
            }
            case "subject" -> {
                //add plitka 1
            }
            case "role" -> {
                //add plitka 2
            }
        }
    }

    @Override
    public SendMessage startRegister(String username) {
        taskMap.put(username, new SurnameTask(new UserDataImpl()));
        SendMessage message = new SendMessage();
        Task task = taskMap.get(username);
        message.setText(task.execute(username, "", waitingPoolDB, alertModule, null));
        taskMap.put(username, task.next());
        return message;
    }
}
