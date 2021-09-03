package ru.image.giga.mars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DialogDictionary {

    private String version;
    private List<Dialog> dialogs;
}