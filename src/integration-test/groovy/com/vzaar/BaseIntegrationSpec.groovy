package com.vzaar

import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification;

public class BaseIntegrationSpec extends Specification {
    protected Vzaar vzaar
    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        vzaar = Vzaar.make(
                System.getenv("vzaarClientId"),
                System.getenv("vzaarAuthToken"))
    }
}
