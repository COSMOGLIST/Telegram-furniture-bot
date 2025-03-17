package ru.suvorin.mappers;

import lombok.experimental.UtilityClass;
import ru.suvorin.models.Furniture;
import ru.suvorin.models.FurnitureInfo;
import ru.suvorin.models.RequestInfo;
import ru.suvorin.models.Request;

@UtilityClass
public class Mapper {
    public static Request map(RequestInfo info) {
        Request userRequest = new Request();
        userRequest.setChatId(info.getChatId());
        userRequest.setLength(info.getLength());
        userRequest.setWidth(info.getWidth());
        userRequest.setHeight(info.getHeight());
        userRequest.setHotelki(info.getHotelki());
        userRequest.setPurpose(info.getPurpose());
        userRequest.setStyle(info.getStyle());
        userRequest.setMoney(info.getMoney());
        return userRequest;
    }

    public static RequestInfo map(Request info) {
        return RequestInfo.builder()
                .width(info.getWidth())
                .length(info.getLength())
                .height(info.getHeight())
                .style(info.getStyle())
                .purpose(info.getPurpose())
                .hotelki(info.getHotelki())
                .chatId(info.getChatId())
                .money(info.getMoney()).build();
    }

    public static FurnitureInfo map(Furniture furniture) {
        return FurnitureInfo.builder()
                .id(furniture.getId())
                .money(furniture.getMoney())
                .category(furniture.getCategory())
                .style(furniture.getStyle())
                .purpose(furniture.getPurpose())
                .description(furniture.getDescription())
                .width(furniture.getWidth())
                .length(furniture.getLength())
                .height(furniture.getHeight())
                .material(furniture.getMaterial())
                .build();
    }

    public static Furniture map(FurnitureInfo furnitureInfo) {
        Furniture furniture = new Furniture();
        furniture.setId(furnitureInfo.getId());
        furniture.setMoney(furnitureInfo.getMoney());
        furniture.setCategory(furnitureInfo.getCategory());
        furniture.setStyle(furnitureInfo.getStyle());
        furniture.setPurpose(furnitureInfo.getPurpose());
        furniture.setDescription(furnitureInfo.getDescription());
        furniture.setWidth(furnitureInfo.getWidth());
        furniture.setLength(furnitureInfo.getLength());
        furniture.setHeight(furnitureInfo.getHeight());
        furniture.setMaterial(furnitureInfo.getMaterial());
        return furniture;
    }
}
