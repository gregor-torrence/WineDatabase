package com.gregortorrence.winedatabase.resources

import spark.Request
import spark.Response
import spock.lang.Specification

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class CommonResourcesSpec extends Specification {

    def 'should handle exceptions by returning the exception message'() {
        given:
        def commonResources = new CommonResources()

        when:
        def response = commonResources.handleNotFoundException(new Exception('message'), Mock(Request), Mock(Response))

        then:
        response == 'message'
    }

}
