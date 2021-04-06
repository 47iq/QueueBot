package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.sql.SQLException;

public class QueueSubjTask implements Task{

    private String subject;

    public QueueSubjTask(String subject) {
        this.subject = subject;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB,
                          AlertModule alertModule, TelegramLongPollingBot bot,
                          UsersDB usersDB, long chat_id, QueueDBManager manager) {
        return "По какому предмету вы хотите записаться в очередь?";
    }

    @Override
    public Task next() {
        return new QueueTask(subject);
    }

    @Override
    public String toString() {
        return "queuesubj";
    }
}
