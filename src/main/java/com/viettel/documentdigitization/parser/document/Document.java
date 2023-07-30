package com.viettel.documentdigitization.parser.document;

import lombok.Data;

import java.util.UUID;

@Data
public class Document extends ContainerItem<Item> {

    private UUID uuid;

    public Document() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String text() {
        return null;
    }
}
