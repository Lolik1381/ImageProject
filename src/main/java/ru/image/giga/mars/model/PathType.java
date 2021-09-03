package ru.image.giga.mars.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum PathType {
    PHOTO ("D:\\GIGAMARS\\TEST\\data", "png"),
    DATA_BOARD ("D:\\GIGAMARS\\TEST\\boards", "png"),
    PDF ("D:\\GIGAMARS\\TEST\\boards", "pdf"),
    BACK ("D:\\GIGAMARS\\TEST\\resource", "back", "png"),
    BOARD ("D:\\GIGAMARS\\TEST\\resource", "board", "png");

    private final String path;
    private String name;
    private final String type;

    public File getFile() throws IOException {
        if (path == null || name == null || type == null) {
            throw new IOException("File cannot be created! Required parameters are missing: path, name or type");
        }

        return new File(String.format("%s\\%s.%s", path, name, type));
    }
}