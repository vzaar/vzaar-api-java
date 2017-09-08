package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class PageSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can convert a json payload to the entity"() {
        given:
        String json = '''
            {
              "data": [
                {
                   "id": 1
                },
                {
                   "id": 2
                }
              ],
              "meta": {
                "links": {
                  "first": "http://api.vzaar.com/api/v2/videos",
                  "last": "http://api.vzaar.com/api/v2/videos?page=4",
                  "next": "http://api.vzaar.com/api/v2/videos?page=2",
                  "previous": null
                },
                "total_count": 4
              }
            }
        '''

        when:
        Page<Map<String, Integer>> entity = mapper.readValue(json, new TypeReference<Page<Map<String, Integer>>>() {})

        then:

        entity.data.size() == 2
        entity.data[0].id == 1
        entity.data[1].id == 2
        entity.totalCount == 4
        entity.hasNext()
        entity.hasLast()
        !entity.hasPrevious()
        with(entity.meta) {
            totalCount == 4
            firstLink == 'http://api.vzaar.com/api/v2/videos'
            hasFirstLink()
            nextLink == 'http://api.vzaar.com/api/v2/videos?page=2'
            hasNextLink()
            lastLink == 'http://api.vzaar.com/api/v2/videos?page=4'
            hasLastLink()
            !hasPreviousLink()
        }
    }
}
