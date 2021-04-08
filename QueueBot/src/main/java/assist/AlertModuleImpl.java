package assist;

import data.Subject;
import data.UsersDB;
import data.WaitingPoolDB;
import inlinekeyboard.KeyboardCreatorImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;

public class AlertModuleImpl implements AlertModule, UTF8Converter {

    private final UsersDB usersDB;
    private final WaitingPoolDB waitingPoolDB;

    public AlertModuleImpl(UsersDB usersDB, WaitingPoolDB waitingPoolDB) {
        this.usersDB = usersDB;
        this.waitingPoolDB = waitingPoolDB;
    }

    @Override
    public void alertRegisterAdmin(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        String text = "Привет, там " + username + " хочет зарегаться. Его данные: " + waitingPoolDB.getInfo(username);
        SendMessage message = getMessage(System.getenv("ADMIN_USERNAME"), text);
        bot.execute(message);
    }

    @Override
    public void alertRegisterAdmin(String username, TelegramLongPollingBot bot, Long chatId) throws TelegramApiException, UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        String group = waitingPoolDB.getGroup(username);
        Long chat_id = usersDB.getAdminChatId(group);
        String text = convert("Привет, там ") + username + convert(" из твоей группы хочет зарегаться. Его данные: ") + waitingPoolDB.getInfo(username);
        message.setChatId(String.valueOf(chat_id));
        //message.setText(convert(text));
        message.setText(text);
        bot.execute(message);
    }

    @Override
    public void alertRegisterUser(String username, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        String text = "Ура. Ваша заявка одобрена. Выберите действие:";
        SendMessage message = getMessage(username, text);
        message.setReplyMarkup(new KeyboardCreatorImpl(usersDB).getInlineKeyboardMarkup(username));
        bot.execute(message);
    }

    @Override
    public void alertQueueUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        if(username == null)
            return;
        String text = "Ваша очередь на сдачу " + subject.toString() + ". Удачи!";
        SendMessage message = getMessage(username, text);
        bot.execute(message);
    }

    @Override
    public void alertSkippedUser(String username, Subject subject, TelegramLongPollingBot bot) throws TelegramApiException, UnsupportedEncodingException {
        if(username == null)
            return;
        String text = "Вас скипнули в очереди на сдачу " + subject.toString() + ":(";
        SendMessage message = getMessage(username, text);
        bot.execute(message);
    }

    @Override
    public void alertRejectUser(String username, TelegramLongPollingBot bot) throws UnsupportedEncodingException, TelegramApiException {
        String text = "К сожалению ваша заявка отклонена. Проверьте данные и попробуйте еще раз.";
        SendMessage message = getMessage(username, text);
        bot.execute(message);
    }

    private SendMessage getMessage(String username, String text) throws UnsupportedEncodingException {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(usersDB.getChatId(username)));
        message.setText(convert(text));
        return message;
    }
}
