package ru.image.giga.mars.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DialogBody {

    private String key;
    private String value;
    private Integer action;
}