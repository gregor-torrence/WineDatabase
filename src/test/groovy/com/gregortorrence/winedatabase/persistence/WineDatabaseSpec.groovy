package com.gregortorrence.winedatabase.persistence

import com.gregortorrence.winedatabase.models.Wine
import spock.lang.Specification

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineDatabaseSpec extends Specification {

    def 'should initialize empty'() {
        when:
        def wineDatabase = new WineDatabase()

        then:
        wineDatabase.readAll().size() == 0
    }

    def 'should create wines'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineDatabase = new WineDatabase()

        when:
        def createdUUID = wineDatabase.create(wine)
        def all = wineDatabase.readAll()

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
        wineDatabase.create(new Wine())
        wineDatabase.create(new Wine())
        wineDatabase.create(new Wine())
        wineDatabase.create(new Wine())

        then:
        wineDatabase.readAll().size() == 5
    }

    def 'should read wines by uuid'() {
        given:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        def wineDatabase = new WineDatabase()
        def createdUUID = wineDatabase.create(wine)

        when:
        def readWine = wineDatabase.read(createdUUID) // Get it? readWine.

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
        def wineDatabase = new WineDatabase()

        when:
        def createdUUID  = wineDatabase.create(wine)
        def createdUUID2 = wineDatabase.create(new Wine())

        then:
        wineDatabase.readAll().size() == 2

        when:
        def readWine = wineDatabase.read(createdUUID)
        readWine.setName('name2').setWinery('winery2').setVarietal('varietal2').setVintage(2017).setAppellation('appellation2')

        and:
        def updated = wineDatabase.update(readWine)

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
        def wineDatabase = new WineDatabase()

        when:
        def createdUUID = wineDatabase.create(wine)

        then:
        wineDatabase.readAll().size() == 1

        when:
        wineDatabase.delete(createdUUID)

        then:
        wineDatabase.readAll().size() == 0

        when: 'double delete is not an error'
        wineDatabase.delete(createdUUID)

        then:
        wineDatabase.readAll().size() == 0
    }

    def 'reading non-existent wines is an error'() {
        given:
        def wineDatabase = new WineDatabase()

        when:
        wineDatabase.read('garbage-uuid')

        then:
        thrown(FileNotFoundException)
    }

    def 'should fail when database in invalid state of duplicate wines'() {
        given:
        def wineDatabase = new WineDatabase()
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')
        wineDatabase.database = [ wine, wine ]

        when:
        wineDatabase.read(wine.getUuid())

        then:
        thrown(IllegalStateException)
    }

}
