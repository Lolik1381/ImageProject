package ru.image.giga.mars;

import ru.image.giga.mars.converter.ImageConverter;

public class Main {

    public static void main(String[] args) {

        try {
            new ImageConverter().createPdfBoards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
