package com.vzaar

import spock.lang.Specification

class VzaarServerExceptionSpec extends Specification {

    def "I can construct the exception"() {
        given:
        VzaarError error = new VzaarError()
        error.message = "error_message"
        error.detail = "error_detail"
        VzaarErrorList errorList = new VzaarErrorList()
        errorList.errors = [error]

        when:
        VzaarServerException e = new VzaarServerException(401, 'Unauthorised', errorList)

        then:
        e.statusCode == 401
        e.statusMessage == 'Unauthorised'
        e.errors.size() == 1
        e.errors.get(0).message == 'error_message'
    }
}
