package com.viettel.documentdigitization.parser.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sentence extends Item {

    private String text;

    @Override
    public String text() {
        return text;
    }
}
