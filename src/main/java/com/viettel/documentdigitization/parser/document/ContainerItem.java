package com.viettel.documentdigitization.parser.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ContainerItem<T extends Item> extends Item {

    protected List<T> children = new ArrayList<>();


    public void addChild(T item) {
        this.children.add(item);
    }


}
