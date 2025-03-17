package ru.suvorin.services;

import ru.suvorin.models.FurnitureFilter;
import ru.suvorin.models.FurnitureInfo;

import java.util.List;

public interface FurnitureService {
    List<FurnitureInfo> getByCriteria(FurnitureFilter filter);
    void addToCartById(Long chatId, Long furnitureId);
    void removeFromCartById(Long chatId, Long furnitureId);
    List<FurnitureInfo> getByChatId(Long chatId);
}
