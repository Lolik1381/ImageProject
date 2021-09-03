package ru.image.giga.mars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Dialog {

    private Integer id;
    private String name;
    private String description;
    private List<DialogBody> body;
}