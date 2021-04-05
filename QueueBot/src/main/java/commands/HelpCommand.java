package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand implements Command{
    public SendMessage execute() {
        SendMessage sendMessage = new SendMessage();
        String message = "/register - подать заявку на регистрацию.\n\n";
        message += "Команды для студентов:\n";
        message += "/queue - зарегистрироваться в очередь. \nСинтаксис: /queue ПРЕДМЕТ. Пример: /queue OPD\n";
        message += "/leave - покинуть очередь. \nСинтаксис: /leave ПРЕДМЕТ. Пример: /leave OPD\n";
        message += "/getqueue - получить текущее состояние очереди. \nСинтаксис /getqueue ПРЕДМЕТ. Пример: /getqueue OPD\n\n";
        message += "Команды для преподавателей:\n";
        message += "/begin - начать прием лабораторной.\n";
        message += "/next - получить следующего сдающего и оповестить его.\n";
        message += "/skip - отправить текущего сдеющего в конец очереди и получить следующего.\n";
        message += "/finish - завершить прием лабораторной.\n";
        sendMessage.setText(message);
        return sendMessage;
    }
}
