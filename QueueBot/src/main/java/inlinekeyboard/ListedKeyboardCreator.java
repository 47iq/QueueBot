package inlinekeyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

public interface ListedKeyboardCreator {
    InlineKeyboardMarkup createInlineKeyBoardMarkUp(List<String> list);
}
