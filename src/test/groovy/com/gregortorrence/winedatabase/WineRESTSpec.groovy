package com.gregortorrence.winedatabase

import com.google.common.util.concurrent.Uninterruptibles
import spark.Spark
import spock.lang.Specification

import java.util.concurrent.TimeUnit

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineRESTSpec extends Specification {

    def 'nothing really happens from main'() {
        given:
        Spark.stop() // might be running from a previous unit test
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS) // give it time to shut down

        when:
        new WineREST().main(new String[0])

        then:
        noExceptionThrown()
    }

}
