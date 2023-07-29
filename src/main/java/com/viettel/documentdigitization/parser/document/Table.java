package com.viettel.documentdigitization.parser.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Table extends ContainerItem<Table.Row> {

    @Data
    public static class Row extends ContainerItem<Cell> {

        @Override
        public String text() {
            return children
                    .stream()
                    .map(Cell::text)
                    .collect(Collectors.joining(", "));
        }

        @Override
        public String toString() {
            return text();
        }
    }

    @Data
    public static class Cell extends ContainerItem<Paragraph> {
        @Override
        public String text() {
            return children
                    .stream()
                    .map(Paragraph::text)
                    .collect(Collectors.joining("\n"));
        }

        @Override
        public String toString() {
            return text();
        }
    }

    @Override
    public String text() {
        return null;
    }
}
