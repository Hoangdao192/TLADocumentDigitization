package com.viettel.documentdigitization.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListHelper {

    public static final <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

}
