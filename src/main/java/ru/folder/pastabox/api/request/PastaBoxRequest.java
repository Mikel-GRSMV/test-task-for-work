package ru.folder.pastabox.api.request;

import lombok.Data;

@Data
public class PastaBoxRequest {
    private String data;
    private long expirationTimeSeconds;
    private PublicStatus publicStatus;
}
