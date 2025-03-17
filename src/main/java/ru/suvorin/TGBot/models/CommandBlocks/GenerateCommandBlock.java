package ru.suvorin.TGBot.models.CommandBlocks;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.models.FurnitureFilter;
import ru.suvorin.models.RequestInfo;
import ru.suvorin.services.FurnitureService;
import ru.suvorin.services.LlamaService;
import ru.suvorin.services.RequestService;

public class GenerateCommandBlock extends CommandBlock {
    private final LlamaService llamaService;
    private final RequestService requestService;
    private final FurnitureService furnitureService;

    public GenerateCommandBlock(String command, String text, String answerText, UserState requiredUserStateForAnswer, LlamaService llamaService, RequestService requestService, FurnitureService furnitureService, InlineKeyboardButton... buttons) {
        super(command, text, answerText, requiredUserStateForAnswer, buttons);
        this.llamaService = llamaService;
        this.requestService = requestService;
        this.furnitureService = furnitureService;
    }

    @Override
    public String handleCommand(long chatId, UsersStateBlock userStatesBlock) {
        userStatesBlock.clearState(chatId);
        RequestInfo requestInfo = requestService.getByChatId(chatId);
        FurnitureFilter furnitureFilter =
                new FurnitureFilter(
                        requestInfo.getStyle(),
                        requestInfo.getPurpose(),
                        requestInfo.getMoney());
        return llamaService.sendRequest(requestInfo, furnitureService.getByCriteria(furnitureFilter));
    }
}
