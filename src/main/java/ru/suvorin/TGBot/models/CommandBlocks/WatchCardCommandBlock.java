package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.services.FurnitureService;

public class WatchCardCommandBlock extends CommandBlock {
    private final FurnitureService furnitureService;
    public WatchCardCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, FurnitureService furnitureService, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
        this.furnitureService = furnitureService;
    }

    @Override
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        StringBuilder stringBuilder = new StringBuilder();
        var furniture = furnitureService.getByChatId(chatId);
        stringBuilder.append(this.getText());
        for (var element : furniture) {
            stringBuilder.append(element.toStringCart()).append('\n');
        }
        return stringBuilder.toString();
    }
}
