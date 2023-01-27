package ru.folder.pastabox.service;

import ru.folder.pastabox.api.request.PastaBoxRequest;
import ru.folder.pastabox.api.response.PastBoxUrlResponse;
import ru.folder.pastabox.api.response.PastaBoxResponse;

import java.util.List;

public interface PastaBoxService {

    PastaBoxResponse getByHash(String hash);

    List<PastaBoxResponse> getFirstPublicPastaBox();

    PastBoxUrlResponse create(PastaBoxRequest request);


}
