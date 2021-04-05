package assist.tasks;

import assist.AlertModule;
import data.Subject;
import data.UserData;
import data.WaitingPoolDB;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class SubmitTask implements Task{

    private final UserData userData;

    public SubmitTask(UserData userData) {
        this.userData = userData;
    }

    @Override
    public String execute(String username, String argument, WaitingPoolDB waitingPoolDB, AlertModule alertModule, TelegramLongPollingBot bot) {
        if(userData.getRole().equals("teacher"))
            userData.setSubject(Subject.forName(argument));
        else
            userData.setSubGroup(Integer.parseInt(argument));
        try {
            waitingPoolDB.register(username, userData.getName(), userData.getSurname(), userData.getRole(),
                    userData.getGroup(), userData.getSubGroup(), userData.getRole().equals("teacher") ? userData.getSubject().toString() : null , userData.getChat_id());
            alertModule.alertRegisterAdmin(username, bot);
            return "Запрос передан на модерацию.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой... Что-то пошло не так";
        }
    }

    @Override
    public Task next() {
        return null;
    }

    @Override
    public String toString() {
        return "submit";
    }
}
