package ru.suvorin.TGBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.suvorin.TGBot.models.CommandBlocks.CommandBlock;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.TGBot.models.UsersStateBlock;
import ru.suvorin.services.LlamaService;
import ru.suvorin.services.RequestService;

import java.util.*;

@Component
public class FurnitureBot extends TelegramLongPollingBot {
    private final List<CommandBlock> commands;
    private final UsersStateBlock usersStateBlock;
    private final LlamaService llamaService;
    private final RequestService requestService;

    @Autowired
    public FurnitureBot(
            @Value("${bot.token}") String botToken,
            LlamaService llamaService, RequestService requestService,
            CommandBlock... commands) {
        super(botToken);
        this.llamaService = llamaService;
        this.requestService = requestService;
        var newCommands = new ArrayList<CommandBlock>();
        Collections.addAll(newCommands, commands);
        this.commands = newCommands;
        usersStateBlock = new UsersStateBlock();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            handleMessage(message, chatId);
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            handleMessage(callbackQuery, chatId);
        }
    }

    private void handleMessage(String message, Long chatId) {
        if (usersStateBlock.contains(chatId)) {
            UserState userState = usersStateBlock.getState(chatId);
            for (CommandBlock block : commands) {
                if (Objects.equals(userState, block.getRequiredUserStateForAnswer())) {
                    sendMessage(chatId, block.handleAnswer(chatId, usersStateBlock, message));
                }
            }
        } else {
            for (CommandBlock block : commands) {
                if (Objects.equals(message, block.getCommand())) {
                    sendMessage(chatId, block.handleCommand(chatId, usersStateBlock), block.getInlineKeyboardMarkup());
                    break;
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "best_mebel_assistant_bot";
    }

    private void sendMessage(Long chatId, String text) {
        if (!Objects.equals(text, "")) {
            var chatIdStr = String.valueOf(chatId);
            var sendMessage = new SendMessage(chatIdStr, text);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void sendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
