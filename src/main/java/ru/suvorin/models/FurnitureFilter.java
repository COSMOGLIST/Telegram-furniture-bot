package ru.suvorin.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FurnitureFilter {
    private String style;
    private String purpose;
    private Long money;
}
