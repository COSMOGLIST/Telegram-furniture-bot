package ru.suvorin.TGBot.models.CommandBlocks;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CommandBlock {
    private final String command;
    private final String text;
    private final String answerText;
    private final InlineKeyboardMarkup inlineKeyboardMarkup;
    private final UserState requiredUserStateForAnswer;

    public CommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, InlineKeyboardButton... buttons) {
        this.command = command;
        this.text = text;
        this.answerText = answerText;
        this.requiredUserStateForAnswer = requiredUserStateForAnswer;
        List<List<InlineKeyboardButton>> buttonList = new ArrayList<>();
        buttonList.add(new ArrayList<>());
        for (InlineKeyboardButton button : buttons) {
            buttonList.getFirst().add(button);
        }
        inlineKeyboardMarkup = new InlineKeyboardMarkup(buttonList);
    }
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        return this.text;
    }
    public String handleAnswer(long chatId, UsersStateBlock userStatesBlock, String answer) {
        return this.answerText;
    }
}
