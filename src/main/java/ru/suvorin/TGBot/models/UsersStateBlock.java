package ru.suvorin.TGBot.models;

import java.util.HashMap;
import java.util.Map;

public class UsersStateBlock {
    private final Map<Long, UserState> userStates = new HashMap<>();

    public void setState(long chatId, UserState state) {
        userStates.put(chatId, state);
    }

    public boolean contains(long chatId) {
        return userStates.containsKey(chatId);
    }
    public UserState getState(long chatId) {
        return userStates.get(chatId);
    }

    public void clearState(long chatId) {
        userStates.remove(chatId);
    }
}
