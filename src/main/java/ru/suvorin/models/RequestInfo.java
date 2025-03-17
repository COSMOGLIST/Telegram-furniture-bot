package ru.suvorin.models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class RequestInfo {
    private Long chatId;
    private Long width;
    private Long length;
    private Long height;
    private String style;
    private String purpose;
    private Long money;
    private String hotelki;
}
