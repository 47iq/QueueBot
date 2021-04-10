package tg_processor;

import assist.UTF8Converter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class QueueBot extends TelegramLongPollingBot {

    private final TGMessageProcessor tgMessageProcessor;

    private final String key;

    public QueueBot(TGMessageProcessor tgMessageProcessor, String key) {
        this.tgMessageProcessor = tgMessageProcessor;
        this.key = key;
    }

    @Override
    public String getBotUsername() {
        return "QueueBot";
    }

    @Override
    public String getBotToken() {
        return key;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
            tgMessageProcessor.process(update, this);
        }
    }
}
