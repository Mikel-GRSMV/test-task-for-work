package ru.folder.pastabox.api.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PastBoxUrlResponse {
    private final String url;
}
