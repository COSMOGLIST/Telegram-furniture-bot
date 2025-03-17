package ru.suvorin.services;

import ru.suvorin.models.RequestInfo;

public interface RequestService {
    void saveRequest(RequestInfo requestInfo);
    RequestInfo getByChatId(Long ChatId);
}
