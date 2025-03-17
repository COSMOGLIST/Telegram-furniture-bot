package ru.suvorin.models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class FurnitureInfo {
    private Long id;
    private Long money;
    private String category;
    private String style;
    private String purpose;
    private String description;
    private Long width;
    private Long length;
    private Long height;
    private String material;

    public String toStringCart() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(' ').append(description).append(' ').append(money.toString());
        return stringBuilder.toString();
    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(id).append("   ")
                .append(category).append(" ")
                .append(description).append(" ")
                .append(height).append(" ")
                .append(length).append(" ")
                .append(material).append(" ")
                .append(money).append(" ")
                .append(purpose).append(" ")
                .append(style).append(" ")
                .append(width).append("\n");
        return stringBuilder.toString();
    }
}
