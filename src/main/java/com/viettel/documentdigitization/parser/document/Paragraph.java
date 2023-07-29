package com.viettel.documentdigitization.parser.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Paragraph extends ContainerItem<Sentence> {

    @Override
    public String text() {
        return children
                .stream()
                .map(Sentence::getText)
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return text();
    }
}
