package ru.suvorin.services;

import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.suvorin.mappers.Mapper;
import ru.suvorin.models.FurnitureFilter;
import ru.suvorin.models.FurnitureInfo;
import ru.suvorin.repositories.FurnitureRepository;
import ru.suvorin.repositories.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@ExtensionMethod(Mapper.class)
public class FurnitureServiceImpl implements FurnitureService {
    private final FurnitureRepository furnitureRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public FurnitureServiceImpl(FurnitureRepository furnitureRepository, RequestRepository requestRepository) {
        this.furnitureRepository = furnitureRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public List<FurnitureInfo> getByCriteria(FurnitureFilter filter) {
        var furniture = furnitureRepository
                .findAllByStyleAndPurposeAndMoneyIsLessThan(
                        filter.getStyle(),
                        filter.getPurpose(),
                        filter.getMoney());
        List<FurnitureInfo> furnitureInfos = new ArrayList<>();
        for (var element : furniture) {
            furnitureInfos.add(element.map());
        }
        return furnitureInfos;
    }

    @Override
    @Transactional
    public void addToCartById(Long chatId, Long furnitureId) {
        var furniture = furnitureRepository.getReferenceById(furnitureId);
        var request = requestRepository.getReferenceById(chatId);
        furniture.addRequest(request);
        request.addFurniture(furniture);
        furnitureRepository.save(furniture);
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public void removeFromCartById(Long chatId, Long furnitureId) {
        var furniture = furnitureRepository.getReferenceById(furnitureId);
        var request = requestRepository.getReferenceById(chatId);
        furniture.removeRequest(request);
        request.removeFurniture(furniture);
        furnitureRepository.save(furniture);
        requestRepository.save(request);
    }

    @Override
    @Transactional
    public List<FurnitureInfo> getByChatId(Long chatId) {
        var request = requestRepository.getReferenceById(chatId);
        var furniture = request.getFurniture();
        List<FurnitureInfo> furnitureInfos = new ArrayList<>();
        for (var element : furniture) {
            furnitureInfos.add(element.map());
        }
        return furnitureInfos;
    }
}
