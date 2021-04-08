package commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class StartCommand implements InfoCommand{
    public SendMessage execute() {
        SendMessage sendMessage = new SendMessage();
        String message = "Приветствую. Для начала работы с ботом необходимо пройти регистрацию /register.\n";
        message += "Заявка будет отмодерирована вручную админом вашей группы(если он зарегистрирован) или админом бота.\n";
        message += "Получить полную справку по командам можно с помощью /help.\n";
        message += "С вопросами и предложениями прошу обращаться к @test_47iq\n";
        message += "Хорошей вам сдачи лаб!\n";
        sendMessage.setText(message);
        return sendMessage;
    }
}
