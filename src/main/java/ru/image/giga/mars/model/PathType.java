package ru.image.giga.mars.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum PathType {
    PHOTO_ENDPOINT("gigamars/data"),
    PDF_ENDPOINT("gigamars/boards"),
    BORDER_CARD("gigamars/resource/borderCard.png"),
    BACK_BOARD("gigamars/resource/backBoard.png"),
    BACK_CARD("gigamars/resource/backCard.png");

    private final String pathToFile;

    public File getFile() throws IOException, URISyntaxException {
        if (pathToFile == null) {
            throw new IOException("File cannot be created! Required parameters are missing: path");
        }

        return getPath().toFile();
    }

    public Path getPath() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource(this.getPathToFile());
        if (Objects.isNull(resource)) {
            throw new IllegalArgumentException(String.format("File %s not found!", this.getPathToFile()));
        }

        return Paths.get(resource.toURI());
    }

//    public String getPathName() {
//        return getResource().getPath();
//    }

    public boolean exist() {
        return Objects.nonNull(getClass().getClassLoader().getResource(this.getPathToFile()));
    }
}