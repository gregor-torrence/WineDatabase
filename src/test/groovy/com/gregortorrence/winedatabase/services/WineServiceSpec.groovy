package com.gregortorrence.winedatabase.services

import com.gregortorrence.winedatabase.models.Wine
import spock.lang.Specification

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineServiceSpec extends Specification {

    def 'should initialize empty'() {
        when:
        def wineService = new WineService()

        then:
        wineService.readAll().size() == 0
    }

    def 'should create wines'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineService = new WineService()

        when:
        def createdUUID = wineService.create(wine)
        def all = wineService.readAll()

        then:
        createdUUID != 'uuid'
        all.size() == 1
        all.get(0).getUuid() == createdUUID
        all.get(0).getName() == 'name'
        all.get(0).getWinery() == 'winery'
        all.get(0).getVarietal() == 'varietal'
        all.get(0).getVintage() == 2015
        all.get(0).getAppellation() == 'appellation'

        when:
        wineService.create(new Wine())
        wineService.create(new Wine())
        wineService.create(new Wine())
        wineService.create(new Wine())

        then:
        wineService.readAll().size() == 5
    }

    def 'should read wines by uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineService = new WineService()
        def createdUUID = wineService.create(wine)

        when:
        def readWine = wineService.read(createdUUID) // Get it? readWine.

        then:
        wine.getUuid() == readWine.getUuid()
        wine.getName() == readWine.getName()
        wine.getWinery() == readWine.getWinery()
        wine.getVarietal() == readWine.getVarietal()
        wine.getVintage() == readWine.getVintage()
        wine.getAppellation() == readWine.getAppellation()
    }

    def 'should update wines by uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineService = new WineService()

        when:
        def createdUUID  = wineService.create(wine)
        def createdUUID2 = wineService.create(new Wine())

        then:
        wineService.readAll().size() == 2

        when:
        def readWine = wineService.read(createdUUID)
        readWine.setName('name2').setWinery('winery2').setVarietal('varietal2').setVintage(2017).setAppellation('appellation2')

        and:
        def updated = wineService.update(readWine)

        then:
        updated.getUuid()        == createdUUID
        updated.getName()        == 'name2'
        updated.getWinery()      == 'winery2'
        updated.getVarietal()    == 'varietal2'
        updated.getVintage()     == 2017
        updated.getAppellation() == 'appellation2'

    }

    def 'should delete wines by uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineService = new WineService()

        when:
        def createdUUID = wineService.create(wine)

        then:
        wineService.readAll().size() == 1

        when:
        wineService.delete(createdUUID)

        then:
        wineService.readAll().size() == 0

        when: 'double delete is not an error'
        wineService.delete(createdUUID)

        then:
        wineService.readAll().size() == 0
    }

    def 'reading non-existent wines is an error'() {
        given:
        def wineService = new WineService()

        when:
        wineService.read('garbage-uuid')

        then:
        thrown(FileNotFoundException)
    }

    def 'should fail when database is in invalid state of duplicate wines'() {
        given:
        def wineService = new WineService()
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        wineService.database = [ wine, wine ]

        when:
        wineService.read(wine.getUuid())

        then:
        thrown(IllegalStateException)
    }

}
