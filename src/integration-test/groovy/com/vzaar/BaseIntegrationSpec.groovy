package com.vzaar

import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification;

public class BaseIntegrationSpec extends Specification {
    protected Vzaar vzaar
    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        vzaar = Vzaar.make(
                System.getProperty("vzaarClientId") ?: System.getenv("vzaarClientId"),
                System.getProperty("vzaarAuthToken") ?: System.getenv("vzaarAuthToken"))
    }
}
