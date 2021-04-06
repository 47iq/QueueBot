package inlinekeyboard;

import assist.UTF8Converter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TeacherInlineKeyboardCreator implements InlineKeyboardCreator, UTF8Converter {

    @Override
    public InlineKeyboardMarkup createInlineKeyBoardMarkUp() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText(convert("Убрать из очереди"));
        inlineKeyboardButton1.setCallbackData("/next");
        inlineKeyboardButton2.setText(convert("Отправить в конец"));
        inlineKeyboardButton2.setCallbackData("/skip");
        inlineKeyboardButton3.setText(convert("Начать прием"));
        inlineKeyboardButton3.setCallbackData("/begin");
        inlineKeyboardButton4.setText(convert("Завершить прием"));
        inlineKeyboardButton4.setCallbackData("/finish");
        inlineKeyboardButton5.setText(convert("Справка"));
        inlineKeyboardButton5.setCallbackData("/help");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        keyboardButtonsRow3.add(inlineKeyboardButton5);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
