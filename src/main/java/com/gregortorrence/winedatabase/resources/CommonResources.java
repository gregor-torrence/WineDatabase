package com.gregortorrence.winedatabase.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import spark.Request;
import spark.Response;
import spark.route.RouteOverview;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

import static spark.Spark.exception;

/**
 * Create common exception handling, and enble route debugging. If we were to support HTTPS, we'd enable it here.
 *
 * Created by Gregor Torrence on 10/16/17.
 */
@Slf4j
public class CommonResources {

    // Package visible for statically importing into other services.
    static final ObjectMapper objectMapper = new ObjectMapper();

    public CommonResources() {
        // Enables https:localhost:8080/debug/routeoverview/ that displays all available endpoints.
        RouteOverview.enableRouteOverview();

        // Handle Exceptions
        exception(FileNotFoundException.class, this::handleNotFoundException);

        log.info("Created common endpoints.");
    }

    private String handleNotFoundException(Exception exception, Request request, Response response) {
        log.info("Service return 404. {}", exception.getMessage());
        response.status(HttpServletResponse.SC_NOT_FOUND);
        return exception.getMessage();
    }

}
