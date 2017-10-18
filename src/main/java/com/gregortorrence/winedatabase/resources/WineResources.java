package com.gregortorrence.winedatabase.resources;

import com.gregortorrence.winedatabase.models.Wine;
import com.gregortorrence.winedatabase.persistence.WineDatabase;
import static com.gregortorrence.winedatabase.resources.CommonResources.*;

import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;
import static spark.Spark.*;

/**
 * CRUD endpoints for Wine documents.
 *
 * Note that endpoint implementations are invoked as method references instead of lambdas. This allow the
 * implementations to be unit-testable.
 *
 * Created by Gregor Torrence on 10/16/17.
 */
@Slf4j
public class WineResources {

    private final WineDatabase wineDatabase;

    public WineResources(WineDatabase wineDatabase) {
        this.wineDatabase = wineDatabase;

        // CRUD
        post("/wine", this::createWine, objectMapper::writeValueAsString);
        get("/wine/:uuid", this::readWine, objectMapper::writeValueAsString);
        put("/wine", this::updateWine, objectMapper::writeValueAsString);
        delete("/wine/:uuid",this::deleteWine);

        // READ ALL (for debugging purposes)
        get("/allWines",this::readAll, objectMapper::writeValueAsString);

        log.info("Created wine CRUD endpoints.");
    }


    private String createWine(Request request, Response response) throws IOException {
        log.info("Create wine  request {}", request.body());
        return wineDatabase.create(objectMapper.readValue(request.body(), Wine.class));
    }

    private Wine readWine(Request request, Response response) throws IOException {
        log.info("Read wine request {}", request.params("uuid"));
        return wineDatabase.read(request.params("uuid"));
    }

    private Wine updateWine(Request request, Response response) throws IOException {
        log.info("Update wine request {}", request.body());
        return wineDatabase.update(objectMapper.readValue(request.body(), Wine.class));
    }

    private String deleteWine(Request request, Response response) {
        log.info("Delete wine request {}", request.params("uuid"));
        wineDatabase.delete(request.params("uuid"));
        return "";
    }

    private List<Wine> readAll(Request request, Response response) {
        log.info("Read all wines request.");
        return wineDatabase.readAll();
    }

}
