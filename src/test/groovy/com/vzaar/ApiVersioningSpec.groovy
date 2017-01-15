package com.vzaar

import spock.lang.Specification

import java.time.ZonedDateTime

class ApiVersioningSpec extends Specification {

    def "I can get the versioning and sunset information"() {
        when:
        ApiVersioning entity = new ApiVersioning([
                ('X-vzaar-Deprecated'): 'TRUE',
                ('X-vzaar-Sunset-Date'): '2018-06-01T00:00:00+00:00'])

        then:
        entity.apiVersionDeprecated
        entity.apiVersionSunsetDate.isEqual(ZonedDateTime.parse('2018-06-01T00:00:00+00:00'))
    }

    def "There is no versioning data"() {
        when:
        ApiVersioning entity = new ApiVersioning([:])

        then:
        !entity.apiVersionDeprecated
        entity.apiVersionSunsetDate == null
    }
}
