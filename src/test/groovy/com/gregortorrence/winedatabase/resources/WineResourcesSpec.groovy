package com.gregortorrence.winedatabase.resources

import com.gregortorrence.winedatabase.models.Wine
import com.gregortorrence.winedatabase.services.WineService
import spark.Request
import spark.Response
import spock.lang.Specification

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineResourcesSpec extends Specification {

    def 'should create in database with expected wine'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def json = CommonResources.objectMapper.writeValueAsString(wine)
        def wineService = Mock(WineService)
        def wineResources = new WineResources(wineService)
        def request = Mock(Request)
        request.body() >> json

        when:
        wineResources.createWine(request, Mock(Response))

        then:
        1 * wineService.create(wine)
    }

    def 'should read wine from database from give uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineService = Mock(WineService)
        def wineResources = new WineResources(wineService)
        def request = Mock(Request)
        request.params('uuid') >> wine.getUuid()

        when:
        wineResources.readWine(request, Mock(Response))

        then:
        1 * wineService.read(wine.getUuid())
    }

    def 'should update in database from expected wine'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def json = CommonResources.objectMapper.writeValueAsString(wine)
        def wineService = Mock(WineService)
        def wineResources = new WineResources(wineService)
        def request = Mock(Request)
        request.body() >> json
        wineResources.createWine(request, Mock(Response))

        when:
        wine.setName('name2').setWinery('winery2').setVarietal('varietal2').setVintage(2017).setAppellation('appellation2')
        request.params('uuid') >> wine.getUuid()
        wineResources.deleteWine(request, Mock(Response))

        then:
        1 * wineService.delete(wine.getUuid())
    }

    def 'should delete in database from uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def json = CommonResources.objectMapper.writeValueAsString(wine)
        def wineService = Mock(WineService)
        def wineResources = new WineResources(wineService)
        def request = Mock(Request)
        request.body() >> json
        wineResources.createWine(request, Mock(Response))

        when:
        wine.setName('name2').setWinery('winery2').setVarietal('varietal2').setVintage(2017).setAppellation('appellation2')
        json = CommonResources.objectMapper.writeValueAsString(wine)
        request.body() >> json
        wineResources.updateWine(request, Mock(Response))

        then:
        1 * wineService.update(wine)
    }

    def 'should read all'() {
        given:
        def wineService = Mock(WineService)
        def wineResources = new WineResources(wineService)

        when:
        wineResources.readAll(Mock(Request), Mock(Response))

        then:
        1 * wineService.readAll()
    }

}
