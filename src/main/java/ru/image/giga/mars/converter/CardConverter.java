package ru.image.giga.mars.converter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import org.imgscalr.Scalr;
import ru.image.giga.mars.model.PathType;
import ru.image.giga.mars.model.Position;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CardConverter {

    private static final List<Position> POSITION_CARDS = List.of(
            new Position(68, 91),
            new Position(896, 91),
            new Position(1718, 91),

            new Position(68, 1263),
            new Position(896, 1263),
            new Position(1718, 1263),

            new Position(68, 2432),
            new Position(896, 2432),
            new Position(1718, 2432)
    );

    public void createPdfBoards() throws IOException, DocumentException, URISyntaxException {
        List<Path> pathsDirectory = Files.list(PathType.PHOTO_ENDPOINT.getPath()).collect(Collectors.toList());
        isDirectory(pathsDirectory);

        for (Path pathDirectory : pathsDirectory) {
            List<Path> pathsFiles = Files.list(Paths.get(pathDirectory.toFile().getPath())).collect(Collectors.toList());

            createPdfBoard(pathsFiles, pathDirectory.getFileName().toString());
        }
    }

    public void createPdfBoard(List<Path> paths, String pdfName) throws IOException, DocumentException, URISyntaxException {
        int numberPhotoOnBoard = 0;
        BufferedImage borderBoard = ImageIO.read(PathType.BORDER_CARD.getFile());

        Document document = new Document(new Rectangle(2480, 3508));

        PdfWriter.getInstance(document, new FileOutputStream(String.format("%s/%s.pdf", PathType.PDF_ENDPOINT.getPath().toString(), pdfName)));
        document.open();

        for (Path path : paths) {
            BufferedImage image = ImageIO.read(path.toFile());
            image = Scalr.crop(image, 174, 203, 2178, 3105);
            image = Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.AUTOMATIC,697, 982);

            borderBoard.getGraphics().drawImage(
                    image,
                    POSITION_CARDS.get(numberPhotoOnBoard).getX(),
                    POSITION_CARDS.get(numberPhotoOnBoard).getY(),
                    null
            );

            numberPhotoOnBoard++;
            if (numberPhotoOnBoard == 9) {
                borderBoard = addDocument(borderBoard, document);

                numberPhotoOnBoard = 0;
            }
        }

        if (numberPhotoOnBoard != 0) {
            addDocument(borderBoard, document);
        }
        addDocument(ImageIO.read(PathType.BACK_BOARD.getFile()), document);

        document.close();
    }

    private BufferedImage addDocument(BufferedImage board, Document document) throws IOException, DocumentException, URISyntaxException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(board, "png", byteArrayOutputStream);
            Image image = Image.getInstance(byteArrayOutputStream.toByteArray());

            image.setAbsolutePosition(0, 0);
            image.scaleToFit(new Rectangle(2480, 3508));
            document.add(image);
            document.newPage();
        }

        return ImageIO.read(PathType.BORDER_CARD.getFile());
    }

    private void isDirectory(List<Path> paths) throws FileSystemException {
        for (Path path : paths) {
            File file = path.toFile();

            if (!file.isDirectory()) {
                throw new FileSystemException("There can be no files in the data folder, only directories!");
            }
        }
    }
}