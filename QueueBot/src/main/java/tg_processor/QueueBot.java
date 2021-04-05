package tg_processor;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class QueueBot extends TelegramLongPollingBot {

    private final TGMessageProcessor tgMessageProcessor;

    public QueueBot(TGMessageProcessor tgMessageProcessor) {
        this.tgMessageProcessor = tgMessageProcessor;
    }

    @Override
    public String getBotUsername() {
        return "QueueBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
                SendMessage defaultMessage = tgMessageProcessor.getAnswer(update, this);
                defaultMessage.setText(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(defaultMessage.getText().getBytes("windows-1251"))).toString());
                if (update.hasCallbackQuery()) {
                    defaultMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
                } else {
                    defaultMessage.setChatId(update.getMessage().getChatId().toString());
                }
                execute(defaultMessage); // Call method to send the message
            }
        }catch (TelegramApiException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
