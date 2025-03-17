package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.services.FurnitureService;

public class AddToCardCommandBlock extends CommandBlock {
    private final FurnitureService furnitureService;
    public AddToCardCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, FurnitureService furnitureService, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
        this.furnitureService = furnitureService;
    }

    @Override
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        userStatesBlock.setState(chatId, getRequiredUserStateForAnswer());
        return this.getText();
    }

    @Override
    public String handleAnswer(long chatId, UsersStateBlock userStatesBlock, String answer) {
        userStatesBlock.clearState(chatId);
        furnitureService.addToCartById(chatId, Long.parseLong(answer));
        return this.getAnswerText();
    }
}
