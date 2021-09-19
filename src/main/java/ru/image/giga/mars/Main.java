package ru.image.giga.mars;

import ru.image.giga.mars.converter.CardConverter;

public class Main {

    public static void main(String[] args) {

        try {
            new CardConverter().createPdfBoards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
