package assist;

import assist.tasks.AccUsernameTask;
import assist.tasks.AcceptTask;
import assist.tasks.SurnameTask;
import assist.tasks.Task;
import data.UserDataImpl;
import data.UsersDB;
import data.WaitingPoolDB;
import inlinekeyboard.InlineKeyboardCreator;
import inlinekeyboard.ListedKeyboardCreator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class TaskManagerImpl implements TaskManager{

    private final Map<String, Task> taskMap = new HashMap<>();

    private final WaitingPoolDB waitingPoolDB;

    private final AlertModule alertModule;

    private final UsersDB usersDB;

    private final InlineKeyboardCreator roleCreator;

    private final InlineKeyboardCreator subGroupCreator;

    private final InlineKeyboardCreator subjectCreator;

    private final ListedKeyboardCreator listCreator;

    public TaskManagerImpl(WaitingPoolDB waitingPoolDB, AlertModule alertModule, UsersDB usersDB, InlineKeyboardCreator roleCreator,
                           InlineKeyboardCreator subGroupCreator, InlineKeyboardCreator subjectCreator,
                           ListedKeyboardCreator listCreator) {
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
        this.usersDB = usersDB;
        this.roleCreator = roleCreator;
        this.subjectCreator = subjectCreator;
        this.subGroupCreator = subGroupCreator;
        this.listCreator = listCreator;
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
        String ans = task.execute(username, arg, waitingPoolDB, alertModule, bot, usersDB);
        SendMessage message = new SendMessage();
        message.setText(ans);
        addKeyBoard(message, task);
        if(task.next() != null)
            taskMap.put(username, task.next());
        else
            clearTasks(username);
        return message;
    }

    private void addKeyBoard(SendMessage message, Task task) {
        switch (task.toString()) {
            case "subgroup" -> {
                message.setReplyMarkup(subGroupCreator.createInlineKeyBoardMarkUp());
            }
            case "subject" -> {
                message.setReplyMarkup(subjectCreator.createInlineKeyBoardMarkUp());
            }
            case "role" -> {
                message.setReplyMarkup(roleCreator.createInlineKeyBoardMarkUp());
            }
            case "accusername" -> {
                message.setReplyMarkup(listCreator.createInlineKeyBoardMarkUp(waitingPoolDB.getUsers()));
            }
        }
    }

    @Override
    public SendMessage startRegister(String username) {
        taskMap.put(username, new SurnameTask(new UserDataImpl()));
        SendMessage message = new SendMessage();
        Task task = taskMap.get(username);
        message.setText(task.execute(username, "", waitingPoolDB, alertModule, null, usersDB));
        taskMap.put(username, task.next());
        return message;
    }

    @Override
    public SendMessage startAccept(String username) {
        taskMap.put(username, new AccUsernameTask(""));
        SendMessage message = new SendMessage();
        Task task = taskMap.get(username);
        addKeyBoard(message, task);
        message.setText(task.execute(username, "", waitingPoolDB, alertModule, null, usersDB));
        taskMap.put(username, task.next());
        return message;
    }

    @Override
    public SendMessage startReject(String username) {
        return null;
    }
}
