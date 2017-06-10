package com.gregor.assessment.persistence;

import com.google.common.collect.ImmutableList;
import com.gregor.assessment.models.Wine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Gregor Torrence on 6/9/17.
 */
public class WineDatabase {

    private static final int INITIAL_CAPACITY = 1024;

    private List<Wine> database = new ArrayList<>(INITIAL_CAPACITY);

    public synchronized String create(Wine wine) {
        String uuid = UUID.randomUUID().toString();
        wine.setUuid(uuid);
        database.add(wine);
        return uuid;
    }

    public synchronized Wine read(String uuid) throws IOException {
        List<Wine> wineList = database.stream().filter(wine -> wine.getUuid().equals(uuid)).collect(Collectors.toList());
        if (wineList.size() == 1) {
            return wineList.get(0);
        } else if (wineList.size() == 0) {
            throw new FileNotFoundException("no wine with UUID " + uuid);
        } else {
            throw new IllegalStateException("More than one wine with UUID " + uuid);
        }
    }

    public synchronized Wine update(Wine wine) {
        database.replaceAll(item -> {
            if (wine.getUuid().equals(item.getUuid()))
                return wine;
            else
                return item;
        });
        return wine;
    }

    public synchronized void delete(String uuid) {
        database = database.stream().filter(wine -> !wine.getUuid().equals(uuid)).collect(Collectors.toList());
    }

    public List<Wine> readAll() {
        return ImmutableList.copyOf(database);
    }

}
