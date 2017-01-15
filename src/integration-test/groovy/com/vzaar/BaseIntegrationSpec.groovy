package com.vzaar

import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification;

public class BaseIntegrationSpec extends Specification {
    protected Vzaar vzaar
    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        println "VzaarClientId=${System.getenv("vzaarClientId")}"
        vzaar = Vzaar.make(
                System.getenv("vzaarClientId"),
                System.getenv("vzaarAuthToken"))
    }
}
