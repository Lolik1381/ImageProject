package ru.image.giga.mars.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum PathType {
    PHOTO_ENDPOINT("gigamars/data"),
    PDF_ENDPOINT("gigamars/boards"),
    BORDER_CARD("gigamars/resource/borderCard.png"),
    BACK_BOARD("gigamars/resource/backBoard.png"),
    BACK_CARD("gigamars/resource/backCard.png");

    private final String path;

    public File getFile() throws IOException {
        if (path == null) {
            throw new IOException("File cannot be created! Required parameters are missing: path");
        }

        return new File(getPathName());
    }

    public URL getResource() {
        URL resource = getClass().getClassLoader().getResource(this.getPath());
        if (Objects.isNull(resource)) {
            throw new IllegalArgumentException(String.format("File %s not found!", this.getPath()));
        }

        return resource;
    }

    public String getPathName() {
        return getResource().getPath();
    }

    public boolean exist() {
        return Objects.nonNull(getClass().getClassLoader().getResource(this.getPath()));
    }
}