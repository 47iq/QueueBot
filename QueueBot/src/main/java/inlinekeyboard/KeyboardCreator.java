package inlinekeyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardCreator {
    InlineKeyboardMarkup getInlineKeyboardMarkup(String username);
}
