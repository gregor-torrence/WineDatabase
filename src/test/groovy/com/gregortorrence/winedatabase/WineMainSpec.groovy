package com.gregortorrence.winedatabase

import com.google.common.util.concurrent.Uninterruptibles
import spark.Spark
import spock.lang.Specification

import java.util.concurrent.TimeUnit

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineMainSpec extends Specification {

    def 'nothing really happens from main'() {
        given:
        Spark.stop() // Probably running from a previous unit test, stop it.
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS) // Give it time to shut down

        when:
        new WineMain().main(new String[0])

        then:
        noExceptionThrown()
    }

}
