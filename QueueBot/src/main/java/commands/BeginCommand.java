package commands;

import data.QueueDBManager;
import exceptions.FatalError;
import inlinekeyboard.InlineKeyboardCreator;
import inlinekeyboard.TeacherInlineKeyboardCreator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BeginCommand implements TeacherCommand{

    private final QueueDBManager queueDBManager;

    public BeginCommand(QueueDBManager queueDBManager) {
        this.queueDBManager = queueDBManager;
    }

    @Override
    public SendMessage execute(String username, TelegramLongPollingBot bot) throws FatalError {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardCreator creator = new TeacherInlineKeyboardCreator();
        try {
            Charset charset = Charset.forName("windows-1251");
            String message = queueDBManager.startQueue(username, bot);
            if(message != null)
                message = charset.decode(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8))).toString();
            else {
                sendMessage.setText("Никто не пришел на фан-встречу((( Очередь пустая.");
                sendMessage.setReplyMarkup(creator.createInlineKeyBoardMarkUp());
                return sendMessage;
            }
            sendMessage.setText("Здравствуйте! Первый сегодня: " + message);
            sendMessage.setReplyMarkup(creator.createInlineKeyBoardMarkUp());
            return sendMessage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FatalError();
        }
    }
}
