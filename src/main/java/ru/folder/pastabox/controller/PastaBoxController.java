package ru.folder.pastabox.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.folder.pastabox.api.request.PastaBoxRequest;
import ru.folder.pastabox.api.response.PastBoxUrlResponse;
import ru.folder.pastabox.api.response.PastaBoxResponse;
import ru.folder.pastabox.service.PastaBoxService;

import java.util.List;

@RestController
public class PastaBoxController {

    private PastaBoxService pastaBoxService;

    @Autowired
    public void setPastaBoxService(PastaBoxService pastaBoxService) {
        this.pastaBoxService = pastaBoxService;
    }

    @GetMapping("/")
    public List<PastaBoxResponse> getPublicPastaList() {
        return pastaBoxService.getFirstPublicPastaBox();
    }

    @GetMapping("/{hash}")
    public PastaBoxResponse getByHash(@PathVariable(name = "hash") String hash) {
        return pastaBoxService.getByHash(hash);
    }

    @PostMapping("/")
    public PastBoxUrlResponse add(@RequestBody PastaBoxRequest request) {
        return pastaBoxService.create(request);
    }
}