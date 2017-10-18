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
 * Create common exception handling, and enable route debugging. If I were to support HTTPS, I'd use a filter
 * to enable it here it here.
 *
 * Created by Gregor Torrence on 10/16/17.
 */
@Slf4j
public class CommonResources {

    // Single ObjectMapper for use everywhere. Handy if I need special options, which I don't yet.
    // Package visible for statically importing into other Resources.
    static final ObjectMapper objectMapper = new ObjectMapper();

    public CommonResources() {
        // Enables https:localhost:8080/debug/routeoverview/ that displays all available endpoints.
        RouteOverview.enableRouteOverview();

        // Handle Exceptions
        exception(FileNotFoundException.class, this::handleNotFoundException);

        log.info("Created common endpoints.");
    }

    private String handleNotFoundException(Exception exception, Request request, Response response) {
        log.info("Service returned 404. {}", exception.getMessage());
        response.status(HttpServletResponse.SC_NOT_FOUND);
        return exception.getMessage();
    }

}
