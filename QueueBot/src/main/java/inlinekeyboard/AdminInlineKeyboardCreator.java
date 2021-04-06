package inlinekeyboard;

import assist.UTF8Converter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class AdminInlineKeyboardCreator implements InlineKeyboardCreator, UTF8Converter {
    @Override
    public InlineKeyboardMarkup createInlineKeyBoardMarkUp() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText(convert("Справка"));
        inlineKeyboardButton1.setCallbackData("/help");
        inlineKeyboardButton2.setText(convert("Записаться в очередь"));
        inlineKeyboardButton2.setCallbackData("/queue");
        inlineKeyboardButton3.setText(convert("Выписаться из очереди"));
        inlineKeyboardButton3.setCallbackData("/leave");
        inlineKeyboardButton4.setText(convert("Порядок очереди"));
        inlineKeyboardButton4.setCallbackData("/getqueue");
        inlineKeyboardButton5.setText(convert("Принять пользователя"));
        inlineKeyboardButton5.setCallbackData("/accept");
        inlineKeyboardButton6.setText(convert("Отклонить пользователя"));
        inlineKeyboardButton6.setCallbackData("/reject");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        keyboardButtonsRow3.add(inlineKeyboardButton5);
        keyboardButtonsRow3.add(inlineKeyboardButton6);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
