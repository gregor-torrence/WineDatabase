package com.gregor.assessment;

/**
 * Created by Gregor Torrence on 6/9/17.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gregor.assessment.models.Wine;
import com.gregor.assessment.persistence.WineDatabase;
import spark.Request;
import spark.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static spark.Spark.*;

public class WineREST {

    private static final WineDatabase wineDatabase = new WineDatabase();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        port(8080);

        // CRUD
        post("/wine",         WineREST::createWine, objectMapper::writeValueAsString);
        get("/wine/:uuid",    WineREST::readWine,   objectMapper::writeValueAsString);
        put("/wine",          WineREST::updateWine, objectMapper::writeValueAsString);
        delete("/wine/:uuid", WineREST::deleteWine);

        // READ ALL
        get("/allWines",       WineREST::readAll,    objectMapper::writeValueAsString);

        // Handle Exceptions
        exception(FileNotFoundException.class, WineREST::handleNotFoundException);
    }

    private static String createWine(Request request, Response response) throws IOException {
        return wineDatabase.create(objectMapper.readValue(request.body(), Wine.class));
    }

    private static Wine readWine(Request request, Response response) throws IOException {
        return wineDatabase.read(request.params("uuid"));
    }

    private static Wine updateWine(Request request, Response response) throws IOException {
        return wineDatabase.update(objectMapper.readValue(request.body(), Wine.class));
    }

    private static String deleteWine(Request request, Response response) {
        wineDatabase.delete(request.params("uuid"));
        return "";
    }

    private static List<Wine> readAll(Request request, Response response) {
        return wineDatabase.readAll();
    }

    private static String handleNotFoundException(Exception exception, Request request, Response response) {
        response.status(HttpServletResponse.SC_NOT_FOUND);
        return exception.getMessage();
    }

}
