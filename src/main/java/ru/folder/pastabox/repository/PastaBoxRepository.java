package ru.folder.pastabox.repository;

import org.springframework.stereotype.Repository;
import ru.folder.pastabox.entity.PastaBoxEntity;

import java.util.List;

public interface PastaBoxRepository {
    PastaBoxEntity getByHash(String hash);

    List<PastaBoxEntity> getListOfPublicAndAlive(int amount);

    void add(PastaBoxEntity pastaBoxEntity);
}
