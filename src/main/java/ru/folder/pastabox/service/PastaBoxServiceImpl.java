package ru.folder.pastabox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.folder.pastabox.api.request.PastaBoxRequest;
import ru.folder.pastabox.api.request.PublicStatus;
import ru.folder.pastabox.api.response.PastBoxUrlResponse;
import ru.folder.pastabox.api.response.PastaBoxResponse;
import ru.folder.pastabox.entity.PastaBoxEntity;
import ru.folder.pastabox.repository.PastaBoxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@ConfigurationProperties(prefix = "app")
public class PastaBoxServiceImpl implements PastaBoxService {

    @Value("${app.host}")
    private String host;
    @Value("${app.public_list_size}")
    private int publicListSize;

    private PastaBoxRepository repository;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Autowired
    public void setPastaBoxRepository(PastaBoxRepository repository) {
        this.repository = repository;
    }

    @Override
    public PastaBoxResponse getByHash(String hash) {
        PastaBoxEntity pastaBoxEntity = repository.getByHash(hash);
        return new PastaBoxResponse(pastaBoxEntity.getData(), pastaBoxEntity.isPublic());
    }

    @Override
    public List<PastaBoxResponse> getFirstPublicPastaBox() {
        List<PastaBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);
        return list.stream().map(pastaBoxEntity ->
                new PastaBoxResponse(pastaBoxEntity.getData(), pastaBoxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PastBoxUrlResponse create(PastaBoxRequest request) {
        int hash = generateId();

        PastaBoxEntity pastaBoxEntity = new PastaBoxEntity();

        pastaBoxEntity.setData(request.getData());
        pastaBoxEntity.setId(hash);
        pastaBoxEntity.setHash(Integer.toHexString(hash));
        pastaBoxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        pastaBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC_STATUS);

        repository.add(pastaBoxEntity);

        return new PastBoxUrlResponse(host + "/" + pastaBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
