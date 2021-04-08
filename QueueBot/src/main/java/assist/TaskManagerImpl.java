package assist;

import assist.tasks.*;
import data.QueueDBManager;
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

    private final QueueDBManager manager;

    public TaskManagerImpl(WaitingPoolDB waitingPoolDB, AlertModule alertModule, UsersDB usersDB, InlineKeyboardCreator roleCreator,
                           InlineKeyboardCreator subGroupCreator, InlineKeyboardCreator subjectCreator,
                           ListedKeyboardCreator listCreator, QueueDBManager queueDBManager) {
        this.waitingPoolDB = waitingPoolDB;
        this.alertModule = alertModule;
        this.usersDB = usersDB;
        this.roleCreator = roleCreator;
        this.subjectCreator = subjectCreator;
        this.subGroupCreator = subGroupCreator;
        this.listCreator = listCreator;
        this.manager = queueDBManager;
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
    public SendMessage executeNextTask(String username, String arg, TelegramLongPollingBot bot, long chat_id) {
        Task task = taskMap.get(username);
        String ans = task.execute(username, arg, waitingPoolDB, alertModule, bot, usersDB, chat_id, manager);
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
            case "group" -> {
                message.setReplyMarkup(listCreator.createInlineKeyBoardMarkUp(usersDB.getGroups()));
            }
            case "subject", "getqueuesubj", "queuesubj", "leavesubj" -> {
                message.setReplyMarkup(subjectCreator.createInlineKeyBoardMarkUp());
            }
            case "role" -> {
                message.setReplyMarkup(roleCreator.createInlineKeyBoardMarkUp());
            }
            case "accusername", "rejectusername" -> {
                message.setReplyMarkup(listCreator.createInlineKeyBoardMarkUp(waitingPoolDB.getUsers()));
            }
        }
    }

    @Override
    public SendMessage startRegister(String username, long chat_id) {
        taskMap.put(username, new SurnameTask(new UserDataImpl()));
        SendMessage message = new SendMessage();
        Task task = taskMap.get(username);
        message.setText(task.execute(username, "", waitingPoolDB, alertModule, null, usersDB, chat_id, manager));
        taskMap.put(username, task.next());
        return message;
    }

    @Override
    public SendMessage startAccept(String username, long chat_id) {
        taskMap.put(username, new AccUsernameTask(""));
        return getSendMessage(username, chat_id);
    }

    @Override
    public SendMessage startReject(String username, long chat_id) {
        taskMap.put(username, new RejectUsernameTask(""));
        return getSendMessage(username, chat_id);
    }

    @Override
    public SendMessage startLeave(String username, long chat_id) {
        taskMap.put(username, new LeaveSubjTask(""));
        return getSendMessage(username, chat_id);
    }

    @Override
    public SendMessage startQueue(String username, long chat_id) {
        taskMap.put(username, new QueueSubjTask(""));
        return getSendMessage(username, chat_id);
    }

    @Override
    public SendMessage startGetQueue(String username, long chat_id) {
        taskMap.put(username, new GetQueueSubjTask(""));
        return getSendMessage(username, chat_id);
    }

    private SendMessage getSendMessage(String username, long chat_id) {
        SendMessage message = new SendMessage();
        Task task = taskMap.get(username);
        addKeyBoard(message, task);
        message.setText(task.execute(username, "", waitingPoolDB, alertModule, null, usersDB, chat_id, manager));
        taskMap.put(username, task.next());
        return message;
    }
}
