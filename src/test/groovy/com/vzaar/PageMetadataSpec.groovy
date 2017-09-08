package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class PageMetadataSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can convert a json payload to the entity"() {
        given:
        String json = '''
            {
                "links": {
                  "first": "http://api.vzaar.com/api/v2/videos",
                  "last": "http://api.vzaar.com/api/v2/videos?page=5",
                  "next": "http://api.vzaar.com/api/v2/videos?page=4",
                  "previous": "http://api.vzaar.com/api/v2/videos?page=2"
                },
                "total_count": 5
            }
        '''

        when:
        PageMetadata entity = mapper.readValue(json, PageMetadata)

        then:
        entity.totalCount == 5
        entity.firstLink == 'http://api.vzaar.com/api/v2/videos'
        entity.hasFirstLink()
        entity.nextLink == 'http://api.vzaar.com/api/v2/videos?page=4'
        entity.hasNextLink()
        entity.lastLink == 'http://api.vzaar.com/api/v2/videos?page=5'
        entity.hasLastLink()
        entity.previousLink == 'http://api.vzaar.com/api/v2/videos?page=2'
        entity.hasPreviousLink()
    }

    def "No previous link"() {
        given:
        String json = '''
            {
                "links": {
                  "first": "http://api.vzaar.com/api/v2/videos",
                  "last": "http://api.vzaar.com/api/v2/videos?page=4",
                  "next": "http://api.vzaar.com/api/v2/videos?page=2",
                  "previous": null
                },
                "total_count": 4
            }
        '''

        when:
        PageMetadata entity = mapper.readValue(json, PageMetadata)

        then:
        entity.totalCount == 4
        entity.hasFirstLink()
        entity.hasNextLink()
        entity.hasLastLink()
        !entity.hasPreviousLink()
    }

    def "No next link"() {
        given:
        String json = '''
            {
                "links": {
                  "first": "http://api.vzaar.com/api/v2/videos",
                  "last": "http://api.vzaar.com/api/v2/videos?page=4",
                  "next": null,
                  "previous": "http://api.vzaar.com/api/v2/videos?page=2"
                },
                "total_count": 4
            }
        '''

        when:
        PageMetadata entity = mapper.readValue(json, PageMetadata)

        then:
        entity.totalCount == 4
        entity.hasFirstLink()
        !entity.hasNextLink()
        entity.hasLastLink()
        entity.hasPreviousLink()
    }

    def "No last link"() {
        given:
        String json = '''
            {
                "links": {
                  "first": "http://api.vzaar.com/api/v2/videos",
                  "last": null,
                  "next": null,
                  "previous": "http://api.vzaar.com/api/v2/videos?page=2"
                },
                "total_count": 4
            }
        '''

        when:
        PageMetadata entity = mapper.readValue(json, PageMetadata)

        then:
        entity.totalCount == 4
        entity.hasFirstLink()
        !entity.hasNextLink()
        !entity.hasLastLink()
        entity.hasPreviousLink()
    }

}
