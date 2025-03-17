package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.models.RequestInfo;
import ru.suvorin.services.RequestService;

public class StartCommandBlock extends CommandBlock {
    private final RequestService requestService;

    public StartCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, RequestService requestService, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
        this.requestService = requestService;
    }

    @Override
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        userStatesBlock.clearState(chatId);
        var builder = RequestInfo.builder();
        builder.chatId(chatId);
        requestService.saveRequest(builder.build());
        return this.getText();
    }
}
