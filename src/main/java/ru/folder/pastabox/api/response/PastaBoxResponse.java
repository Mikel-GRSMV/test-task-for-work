package ru.folder.pastabox.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PastaBoxResponse {
    private final String data;
    private final boolean isPublic;
}
