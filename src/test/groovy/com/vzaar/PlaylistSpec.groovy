package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class PlaylistSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "data": {
                    "id": 10,
                    "category_id": 42,
                    "title": "test",
                    "sort_order": "desc",
                    "sort_by": "created_at",
                    "max_vids": 43,
                    "position": "right",
                    "private": true,
                    "dimensions": "768x340",
                    "autoplay": true,
                    "continuous_play": true,
                    "created_at": "2015-04-06T22:03:24.000Z",
                    "updated_at": "2016-01-06T12:08:38.000Z"
                }
            }
       '''

        when:
        Lookup<Playlist> entity = mapper.readValue(payload, new TypeReference<Lookup<Playlist>>() {});

        then:
        with(entity.data) {
            id == 10
            categoryId == 42
            title =='test'
            sortOrder == SortDirection.desc
            sortBy == 'created_at'
            maxVids == 43
            position == ControlsPosition.right
            isPrivate
            dimensions == '768x340'
            autoplay
            continuousPlay
            createdAt.isEqual(ZonedDateTime.parse("2015-04-06T22:03:24.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2016-01-06T12:08:38.000Z"))
        }
    }
}
