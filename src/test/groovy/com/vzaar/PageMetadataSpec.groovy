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
                  "first": "https://app.raazv.com/api/v2/videos",
                  "last": "https://app.raazv.com/api/v2/videos?page=5",
                  "next": "https://app.raazv.com/api/v2/videos?page=4",
                  "previous": "https://app.raazv.com/api/v2/videos?page=2"
                },
                "total_count": 5
            }
        '''

        when:
        PageMetadata entity = mapper.readValue(json, PageMetadata)

        then:
        entity.totalCount == 5
        entity.firstLink == 'https://app.raazv.com/api/v2/videos'
        entity.hasFirstLink()
        entity.nextLink == 'https://app.raazv.com/api/v2/videos?page=4'
        entity.hasNextLink()
        entity.lastLink == 'https://app.raazv.com/api/v2/videos?page=5'
        entity.hasLastLink()
        entity.previousLink == 'https://app.raazv.com/api/v2/videos?page=2'
        entity.hasPreviousLink()
    }

    def "No previous link"() {
        given:
        String json = '''
            {
                "links": {
                  "first": "https://app.raazv.com/api/v2/videos",
                  "last": "https://app.raazv.com/api/v2/videos?page=4",
                  "next": "https://app.raazv.com/api/v2/videos?page=2",
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
                  "first": "https://app.raazv.com/api/v2/videos",
                  "last": "https://app.raazv.com/api/v2/videos?page=4",
                  "next": null,
                  "previous": "https://app.raazv.com/api/v2/videos?page=2"
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
                  "first": "https://app.raazv.com/api/v2/videos",
                  "last": null,
                  "next": null,
                  "previous": "https://app.raazv.com/api/v2/videos?page=2"
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
