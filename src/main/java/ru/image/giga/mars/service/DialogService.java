package ru.image.giga.mars.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.image.giga.mars.model.Dialog;
import ru.image.giga.mars.model.DialogBody;
import ru.image.giga.mars.model.DialogDictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class DialogService {
    private static DialogService instance;
    private DialogService() {}

    private List<Dialog> dialogs;
    private int lastAction = 0;

    public static DialogService getInstance() {
        if (instance == null) {
            instance = new DialogService();

            instance.initialize();
        }

        return instance;
    }

    public DialogService start() {
        Dialog dialog = dialogs.stream().filter(findDialog -> findDialog.getId() == lastAction)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("No start dialog found!"));

        lastAction = printDialog(dialog);
        return instance;
    }

    private int printDialog(Dialog dialog) {
        dialog.getBody().forEach(dialogBody -> {
            System.out.printf("%s. %s%n", dialogBody.getKey(), dialogBody.getValue());
        });

        System.out.print("-> ");
        return userAction(dialog);
    }

    private int userAction(Dialog dialog) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            return dialog.getBody()
                    .get(Integer.parseInt(bufferedReader.readLine()))
                    .getAction();
        } catch (IOException e) {
            System.out.println("Не удалось вас понять!\nПопробуйте еще раз!");

            return 0;
        }
    }

    private void initialize() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            DialogDictionary dialog = objectMapper.readValue("", DialogDictionary.class);

            instance.dialogs = dialog.getDialogs();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}