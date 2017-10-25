package com.gregortorrence.winedatabase.persistence;

import com.google.common.collect.ImmutableList;
import com.gregortorrence.winedatabase.models.Wine;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This application is a Sparkjava demo, not a persistence demo. So I'll use an impractical in-memory persistence model.
 *
 * Created by Gregor Torrence on 6/9/17.
 */
@Slf4j
public class WineService {

    private static final int INITIAL_CAPACITY = 1024;

    private List<Wine> database = new ArrayList<>(INITIAL_CAPACITY);

    public synchronized String create(Wine wine) {
        log.info("Creating wine {}", wine);
        String uuid = UUID.randomUUID().toString();
        wine.setUuid(uuid);
        database.add(wine);
        return uuid;
    }

    public synchronized Wine read(String uuid) throws IOException {
        log.info("Reading wine {}", uuid);
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
        log.info("Updating wine {}", wine);
        database.replaceAll(item -> {
            if (wine.getUuid().equals(item.getUuid()))
                return wine;
            else
                return item;
        });
        return wine;
    }

    public synchronized void delete(String uuid) {
        log.info("Deleting wine {}", uuid);
        database = database.stream().filter(wine -> !wine.getUuid().equals(uuid)).collect(Collectors.toList());
    }

    public synchronized List<Wine> readAll() {
        log.info("Reading all wines.");
        return ImmutableList.copyOf(database);
    }

}
