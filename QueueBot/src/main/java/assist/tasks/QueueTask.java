package assist.tasks;

import assist.AlertModule;
import data.QueueDBManager;
import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.sql.SQLException;

public class QueueTask implements Task{

    private String subject;

    public QueueTask(String subject) {
        this.subject = subject;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB,
                          AlertModule alertModule, TelegramLongPollingBot bot,
                          UsersDB usersDB, long chat_id, QueueDBManager manager) {
        subject = argument;
        try {
            manager.add(username, Subject.forName(subject));
            return ("Вы успешно записались в очередь. Посмотреть ее можно командой /getqueue");
        } catch (SQLException e) {
            return ("Ой. Что-то пошло не так. Возможно, вы уже записаны в очередь. Если нет, то напишите пж @true_47iq");
        } catch (IllegalArgumentException e) {
            return ("Вашего предмета нет в списке. Он должен быть одним из: {OPD, Programming}.");
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "queue";
    }
}
