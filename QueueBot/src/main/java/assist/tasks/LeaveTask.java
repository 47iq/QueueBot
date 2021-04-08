package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import exceptions.FatalError;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.sql.SQLException;

public class LeaveTask implements Task{

    private String subject;

    public LeaveTask(String subject) {
        this.subject = subject;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB,
                          AlertModule alertModule, TelegramLongPollingBot bot,
                          UsersDB usersDB, long chat_id, QueueDBManager manager) throws FatalError {
        subject = argument;
        try {
            manager.remove(username, Subject.forName(subject));
            return ("Вы успешно вышли из очереди.");
        } catch (IllegalArgumentException e) {
            return ("Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new FatalError();
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "leave";
    }
}
