package com.gregortorrence.winedatabase;

import com.gregortorrence.winedatabase.services.WineService;
import com.gregortorrence.winedatabase.resources.CommonResources;
import com.gregortorrence.winedatabase.resources.WineResources;
import lombok.extern.slf4j.Slf4j;

import static spark.Spark.port;

/**
 * Main method. Instantiates everything and starts Jetty.
 *
 * Created by Gregor Torrence on 6/9/17.
 */
@Slf4j
public class WineMain {

    public static void main(String[] args) {
        port(8080);

        // Initialize services
        WineService wineService = new WineService();

        // Create HTTP resources
        new CommonResources();
        new WineResources(wineService);

        log.info("WineService Jetty started.");
    }

}
