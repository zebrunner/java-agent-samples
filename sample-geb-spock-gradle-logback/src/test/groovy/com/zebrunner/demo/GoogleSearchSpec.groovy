package com.zebrunner.demo


import geb.spock.GebSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.lang.invoke.MethodHandles

class GoogleSearchSpec extends GebSpec {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    def "open up browser on www.google.com"() {
        given:
        LOGGER.info("open up www.google.com")
        to GoogleHomePage

        expect:
        1 == 1
    }

    def cleanupSpec() {
        driver.quit()
    }

}
