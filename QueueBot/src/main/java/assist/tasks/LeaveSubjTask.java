package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class LeaveSubjTask implements Task{

    private String subject;

    public LeaveSubjTask(String subject) {
        this.subject = subject;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB,
                          AlertModule alertModule, TelegramLongPollingBot bot,
                          UsersDB usersDB, long chat_id, QueueDBManager manager) {
        return "По какому предмету вы хотите покинуть очередь?";
    }

    @Override
    public Task next() {
        return new LeaveTask(subject);
    }

    @Override
    public String toString() {
        return "leavesubj";
    }
}