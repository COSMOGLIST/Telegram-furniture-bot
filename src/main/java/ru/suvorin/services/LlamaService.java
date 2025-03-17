package ru.suvorin.services;

import ru.suvorin.models.FurnitureInfo;
import ru.suvorin.models.RequestInfo;

import java.util.List;

public interface LlamaService {
    public String sendRequest(RequestInfo requestInfo, List<FurnitureInfo> furnitureInfos);
}
