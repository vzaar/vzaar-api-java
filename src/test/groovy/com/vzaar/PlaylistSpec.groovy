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
                    "private": true,
                    "position": "right",
                    "private": true,
                    "dimensions": "768x340",
                    "autoplay": true,
                    "continuous_play": true,
                    "embed_code": "<html>embed</html>",
                    "videos": [
                        {
                            "id": 7574853,
                            "title": "multipart",
                            "user_id": 42,
                            "description": "",
                            "duration": 5.6,
                            "created_at": "2016-11-11T11:36:26.000Z",
                            "updated_at": "2016-11-11T11:36:26.000Z",
                            "private": false
                        },
                        {
                            "id": 7574854,
                            "title": "multipart",
                            "user_id": 42,
                            "description": "",
                            "duration": 5.6,
                            "created_at": "2016-11-11T11:36:26.000Z",
                            "updated_at": "2016-11-11T11:36:26.000Z",
                            "private": false
                        }
                    ],
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
            isPrivate()
            position == ControlsPosition.right
            isPrivate
            dimensions == '768x340'
            embedCode == '<html>embed</html>'
            autoplay
            continuousPlay
            createdAt.isEqual(ZonedDateTime.parse("2015-04-06T22:03:24.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2016-01-06T12:08:38.000Z"))
            videos.size() == 2
        }
    }
}
