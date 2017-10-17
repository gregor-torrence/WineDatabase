package com.gregortorrence.winedatabase.models

import spock.lang.Specification

/**
 * Created by Gregor Torrence on 10/16/17.
 */
class WineSpec extends Specification {
    
    def 'should do all those POJO things'() {
        when:
        def wine = new Wine().setUuid('uuid').setName('name').setWinery('winery').setVarietal('varietal').setVintage(2015).setAppellation('appellation')

        then:
        wine.getUuid()        == 'uuid'
        wine.getName()        == 'name'
        wine.getWinery()      == 'winery'
        wine.getVarietal()    == 'varietal'
        wine.getVintage()     == 2015
        wine.getAppellation() == 'appellation'

        and:
        // Silly stuff to make code coverage realistic
        wine.equals(wine)
        wine.hashCode() == wine.hashCode()
        wine.toString().length() > 0
    }
    
}
