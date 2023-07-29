package com.viettel.documentdigitization.parser.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Item {

    private int page;
    private Item parent;

    public abstract String text();
}
