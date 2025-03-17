package ru.suvorin.services;

import com.asierso.llamaapi.LlamaConnection;
import com.asierso.llamaapi.builder.LlamaPromptsBuilder;
import com.asierso.llamaapi.builder.LlamaRequestBaseBuilder;
import com.asierso.llamaapi.handlers.LlamaConnectionException;
import com.asierso.llamaapi.handlers.LlamaRequestBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.suvorin.models.FurnitureInfo;
import ru.suvorin.models.RequestInfo;

import java.util.List;

@Service
public class LLamaServiceImpl implements LlamaService {

    @Value("${llama.url}")
    private String llamaUrl;

    private String requestPart1 = "Ты — профессиональный дизайнер интерьеров. На основе предоставленных данных создай текстовое описание дизайна комнаты. \n" +
            "Учти параметры: \n" +
            "- Размеры комнаты: Ширина — ";
    private String requestPart2 = " м, Длина — ";
    private String requestPart3 = " м, Высота — ";

    private String requestPart4 = " м. \n- Назначение: ";
    private String requestPart5 = " \n- Стиль: ";
    private String requestPart6 = " \n- Пожелания: ";
    private String requestPart7 = " \n- Бюджет: ";

    private String requestPart8 = """
             рублей.
                        "Задача: " +
                        1. Опиши, как будет выглядеть комната:
                        - Цветовая гамма и материалы для стен, пола, потолка.
                        - Расположение мебели с учетом габаритов комнаты (укажи примерные размеры предметов).
                        - Освещение и декор, соответствующие стилю.
                        - Решение по окнам/дверям, если они упомянуты в пожеланиях.
                        2. Сделай акцент на:
                        - Оптимизацию пространства (например: "Узкий шкаф-купе глубиной 0.6 м вдоль стены сэкономит место").
                        - Функциональность и эргономику (расстояние между предметами, проходы).
                        - Соответствие бюджету (например: "Подиум для зонирования можно сделать из недорогого ламината").
                        Важно:
                        - Мебель должна физически помещаться в комнату (не предлагай диван длиной 3 м для комнаты шириной 2.5 м).
                        - Учитывай высоту потолков (например, не предлагай высокие стеллажи, если высота комнаты 2.4 м).
                        Ответь, пожалуйста, на русском языке.
                        В своем выборе мебели используй следующую таблицу, и выведи в конце ответа списком всю выбранную тобой мебель для этой комнаты.
                        Важно, чтобы ты верно указал id и description, чтобы я потом мог найти нужную мне мебель по id.
                        Таблица:
                        id	category	description	height	length	material	money	purpose	style	width
            """;
    @Override
    public String sendRequest(RequestInfo requestInfo, List<FurnitureInfo> furnitureInfos){
        LlamaConnection connection = new LlamaConnection(llamaUrl);
        LlamaRequestBase requestBase = new LlamaRequestBaseBuilder()
                .useModel("llama3")
                .withStream(false)
                .build();
        try {
            var builder = new LlamaPromptsBuilder(requestBase)
                    .appendPrompt(requestPart1)
                    .appendPrompt(String.valueOf(requestInfo.getWidth()))
                    .appendPrompt(requestPart2)
                    .appendPrompt(String.valueOf(requestInfo.getLength()))
                    .appendPrompt(requestPart3)
                    .appendPrompt(String.valueOf(requestInfo.getHeight()))
                    .appendPrompt(requestPart4)
                    .appendPrompt(requestInfo.getPurpose())
                    .appendPrompt(requestPart5)
                    .appendPrompt(requestInfo.getStyle())
                    .appendPrompt(requestPart6)
                    .appendPrompt(requestInfo.getHotelki())
                    .appendPrompt(requestPart7)
                    .appendPrompt(String.valueOf(requestInfo.getMoney()))
                    .appendPrompt(requestPart8);
            for (var furnitureInfo : furnitureInfos) {
                builder.appendPrompt(furnitureInfo.toString());
            }
            return connection.fetch(builder.build()).getResponse();
        } catch (LlamaConnectionException exception) {
            return "Ошибка подключения! Пожалуйста, попробуйте позже";
        }
    }
}
