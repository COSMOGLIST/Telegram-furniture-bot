package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.services.RequestService;

public class HeightCommandBlock extends CommandBlock {
    private final RequestService requestService;

    public HeightCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, RequestService requestService, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
        this.requestService = requestService;
    }

    @Override
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        userStatesBlock.setState(chatId, getRequiredUserStateForAnswer());
        return this.getText();
    }

    @Override
    public String handleAnswer(long chatId, UsersStateBlock userStatesBlock, String answer) {
        userStatesBlock.clearState(chatId);
        var request = requestService.getByChatId(chatId);
        request.setHeight(Long.parseLong(answer));
        requestService.saveRequest(request);
        return this.getAnswerText();
    }
}
