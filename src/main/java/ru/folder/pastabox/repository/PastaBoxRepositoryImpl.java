package ru.folder.pastabox.repository;

import org.springframework.stereotype.Repository;
import ru.folder.pastabox.entity.PastaBoxEntity;
import ru.folder.pastabox.exception.NotFoundEntityException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PastaBoxRepositoryImpl implements PastaBoxRepository {

    private final Map<String, PastaBoxEntity> vault = new HashMap<>();

    @Override
    public PastaBoxEntity getByHash(String hash) {
        PastaBoxEntity pastaBoxEntity = vault.get(hash);

        if (pastaBoxEntity == null) {
            throw new NotFoundEntityException("PastaBox not found with hash= " + hash);
        }
        return pastaBoxEntity;
    }

    @Override
    public List<PastaBoxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime nowTimestamp = LocalDateTime.now();

        return vault.values().stream()
                .filter(PastaBoxEntity::isPublic)
                .filter(pastaBoxEntity -> pastaBoxEntity.getLifeTime().isAfter(nowTimestamp))
                .sorted(Comparator.comparing(PastaBoxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PastaBoxEntity pastaBoxEntity) {
        vault.put(pastaBoxEntity.getHash(), pastaBoxEntity);
    }
}
