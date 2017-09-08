package com.vzaar

import spock.lang.Specification

class VzaarExceptionSpec extends Specification {

    def "I can create an exception with just a message"() {
        when:
        VzaarException e = new VzaarException("error message")

        then:
        e.message == "error message"
    }

    def "I can create an exception with just a cause"() {
        given:
        IllegalArgumentException cause = new IllegalArgumentException();
        when:
        VzaarException e = new VzaarException(cause)

        then:
        e.cause == cause
    }

}
