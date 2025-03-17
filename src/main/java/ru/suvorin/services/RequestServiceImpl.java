package ru.suvorin.services;

import lombok.experimental.ExtensionMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.suvorin.mappers.Mapper;
import ru.suvorin.models.RequestInfo;
import ru.suvorin.repositories.RequestRepository;

@Service
@ExtensionMethod(Mapper.class)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    @Transactional
    public void saveRequest(RequestInfo requestInfo) {
        requestRepository.save(requestInfo.map());
    }

    @Override
    @Transactional
    public RequestInfo getByChatId(Long chatId) {
        return requestRepository.getReferenceById(chatId).map();
    }
}
