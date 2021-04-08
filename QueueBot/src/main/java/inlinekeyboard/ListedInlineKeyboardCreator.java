package inlinekeyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ListedInlineKeyboardCreator implements ListedKeyboardCreator{

    private final int BUTTONS_IN_ROW = 2;

    private final int MAX_ROWS = 3;

    @Override
    public InlineKeyboardMarkup createInlineKeyBoardMarkUp(List<String> list) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for(var username: list) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(username);
            button.setCallbackData(username);
            buttons.add(button);
        }
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        int rowCounter = 0;
        int counter = 0;
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (var button: buttons) {
            counter++;
            row.add(button);
            if(counter == BUTTONS_IN_ROW) {
                counter = 0;
                rows.add(row);
                row = new ArrayList<>();
                rowCounter++;
                if(rowCounter == MAX_ROWS)
                    break;
            }
        }
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
