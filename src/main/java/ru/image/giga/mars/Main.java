package ru.image.giga.mars;

import com.itextpdf.text.DocumentException;
import ru.image.giga.mars.converter.ImageConverter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, DocumentException {

        new ImageConverter().createPdfBoards();
    }
}
