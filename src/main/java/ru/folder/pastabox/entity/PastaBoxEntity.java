package ru.folder.pastabox.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PastaBoxEntity {
    private int id;
    private String data;
    private String hash;
    private LocalDateTime lifeTime;
    private boolean isPublic;
}
