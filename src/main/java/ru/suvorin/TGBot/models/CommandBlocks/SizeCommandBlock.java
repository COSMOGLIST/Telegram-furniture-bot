package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;

public class SizeCommandBlock extends CommandBlock {

    public SizeCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
    }
}
