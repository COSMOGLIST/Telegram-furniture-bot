package ru.suvorin.TGBot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.suvorin.TGBot.bot.FurnitureBot;
import ru.suvorin.TGBot.models.CommandBlocks.*;
import ru.suvorin.TGBot.models.UserState;
import ru.suvorin.services.FurnitureService;
import ru.suvorin.services.LlamaService;
import ru.suvorin.services.RequestService;

@Configuration
public class FurnitureBotConfiguration {

    @Bean
    public CommandBlock start(RequestService requestService) {
        var button1 = new InlineKeyboardButton("Стиль");
        button1.setCallbackData("/style");
        var button2 = new InlineKeyboardButton("Предназначение");
        button2.setCallbackData("/purpose");
        var button3 = new InlineKeyboardButton("Особые пожелания");
        button3.setCallbackData("/hotelki");
        var button4 = new InlineKeyboardButton("Бюджет");
        button4.setCallbackData("/money");
        var button5 = new InlineKeyboardButton("Размеры");
        button5.setCallbackData("/size");
        return new StartCommandBlock(
                "/start",
                "Добро пожаловать в выбор мебели 2000! Давайте подберем мебель для вашей комнаты",
                "",
                UserState.NONE,
                requestService,
                button1,
                button2,
                button3,
                button4,
                button5
        );
    }

    @Bean
    public CommandBlock purpose(RequestService requestService) {
        var button1 = new InlineKeyboardButton("Гостиная");
        button1.setCallbackData("Гостиная");
        var button2 = new InlineKeyboardButton("Спальня");
        button2.setCallbackData("Спальня");
        var button3 = new InlineKeyboardButton("Ванная");
        button3.setCallbackData("Ванная");
        var button4 = new InlineKeyboardButton("Кухня");
        button4.setCallbackData("Кухня");
        return new PurposeCommandBlock(
                "/purpose",
                "Какое предназначение у вашей комнаты?",
                "Предназначение учтено",
                UserState.PURPOSE_WAIT,
                requestService,
                button1,
                button2,
                button3,
                button4
        );
    }

    @Bean
    public CommandBlock style(RequestService requestService) {
        var button1 = new InlineKeyboardButton("Минимализм");
        button1.setCallbackData("Минимализм");
        var button2 = new InlineKeyboardButton("Классика");
        button2.setCallbackData("Классика");
        var button3 = new InlineKeyboardButton("Дерево");
        button3.setCallbackData("Дерево");
        var button4 = new InlineKeyboardButton("Лофт");
        button4.setCallbackData("Лофт");
        return new StyleCommandBlock(
                "/style",
                "Выберите стиль, в котором вы хотите обставить свою комнату",
                "Стиль учтён",
                UserState.STYLE_WAIT,
                requestService,
                button1,
                button2,
                button3,
                button4
        );
    }

    @Bean
    public CommandBlock size() {
        var button1 = new InlineKeyboardButton("Высота");
        button1.setCallbackData("/sizeHeight");
        var button2 = new InlineKeyboardButton("Ширина");
        button2.setCallbackData("/sizeWidth");
        var button3 = new InlineKeyboardButton("Длина");
        button3.setCallbackData("/sizeLength");
        return new SizeCommandBlock(
                "/size",
                "Укажите размеры вашей комнаты",
                "",
                UserState.NONE,
                button1,
                button2,
                button3
        );
    }

    @Bean
    public CommandBlock sizeLength(RequestService requestService) {
        return new LengthCommandBlock(
                "/sizeLength",
                "Напишите длину вашей комнаты",
                "Длина записана",
                UserState.LENGTH_WAIT,
                requestService
        );
    }

    @Bean
    public CommandBlock sizeWidth(RequestService requestService) {
        return new WidthCommandBlock(
                "/sizeWidth",
                "Напишите ширину вашей комнаты",
                "Ширина записана",
                UserState.WIDTH_WAIT,
                requestService
        );
    }

    @Bean
    public CommandBlock sizeHeight(RequestService requestService) {
        return new HeightCommandBlock(
                "/sizeHeight",
                "Напишите высоту вашей комнаты",
                "Высота записана",
                UserState.HEIGHT_WAIT,
                requestService
        );
    }

    @Bean
    public CommandBlock hotelki(RequestService requestService) {
        return new HotelkiCommandBlock(
                "/hotelki",
                "Особые пожелания?",
                "Пожелания учтены",
                UserState.HOTELKI_WAIT,
                requestService
        );
    }

    @Bean
    public CommandBlock money(RequestService requestService) {
        return new MoneyCommandBlock(
                "/money",
                "Какой у вас бюджет?",
                "Бюджет учтен",
                UserState.MONEY_WAIT,
                requestService
        );
    }

    @Bean
    public CommandBlock generate(LlamaService llamaService, RequestService requestService, FurnitureService furnitureService) {
        return new GenerateCommandBlock(
                "/generate",
                "Генерируем ответ, пожалуйста подождите",
                "",
                UserState.NONE,
                llamaService,
                requestService,
                furnitureService

        );
    }

    @Bean
    public CommandBlock addToCart(FurnitureService furnitureService) {
        return new AddToCardCommandBlock(
                "/add",
                "Что хотите добавить?",
                "Добавлено в вашу корзину",
                UserState.CART_ADD_WAIT,
                furnitureService
        );
    }

    @Bean
    public CommandBlock removeFromCart(FurnitureService furnitureService) {
        return new RemoveFromCardCommandBlock(
                "/remove",
                "Что хотите убрать?",
                "Убрано из вашей корзины",
                UserState.CART_REMOVE_WAIT,
                furnitureService
        );
    }

    @Bean
    public CommandBlock watchCart(FurnitureService furnitureService) {
        return new WatchCardCommandBlock(
                "/watch",
                "Ваша корзина: \n",
                "",
                UserState.NONE,
                furnitureService
        );
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(FurnitureBot furnitureBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(furnitureBot);
        return api;
    }
}
