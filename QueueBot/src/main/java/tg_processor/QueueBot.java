package tg_processor;

import assist.UTF8Converter;
import inlinekeyboard.InlineKeyboardCreator;
import inlinekeyboard.KeyboardCreator;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class QueueBot extends TelegramLongPollingBot implements UTF8Converter {

    private final TGMessageProcessor tgMessageProcessor;

    private final KeyboardCreator keyboardCreator;

    public QueueBot(TGMessageProcessor tgMessageProcessor, KeyboardCreator keyboardCreator) {
        this.tgMessageProcessor = tgMessageProcessor;
        this.keyboardCreator = keyboardCreator;
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
                execute(defaultMessage);
                if(defaultMessage.getReplyMarkup() == null) {
                    SendMessage message = new SendMessage();
                    String username;
                    long chatId;
                    if(update.hasCallbackQuery()) {
                        chatId = update.getCallbackQuery().getMessage().getChatId();
                        username = update.getCallbackQuery().getFrom().getUserName();
                    }
                    else {
                        chatId = update.getMessage().getChatId();
                        username = update.getMessage().getFrom().getUserName();
                    }
                    message.setChatId(String.valueOf(chatId));
                    message.setText(convert("Выберите действие:"));
                    InlineKeyboardMarkup markup = keyboardCreator.getInlineKeyboardMarkup(username);
                    message.setReplyMarkup(markup);
                    execute(message);
                }
            }
        }catch (TelegramApiException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
