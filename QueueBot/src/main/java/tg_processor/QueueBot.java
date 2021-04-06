package tg_processor;

import assist.UTF8Converter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class QueueBot extends TelegramLongPollingBot implements UTF8Converter {

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
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {
            tgMessageProcessor.process(update, this);
        }
    }
}
