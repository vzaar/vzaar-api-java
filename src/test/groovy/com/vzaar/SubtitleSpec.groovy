package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class SubtitleSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "data": {
                    "id": 123,
                    "code": "en",
                    "title": "english-subtitles.srt",
                    "language": "English",
                    "created_at": "2017-09-06T21:20:27.000Z",
                    "updated_at": "2017-09-06T21:20:27.000Z",
                    "url": "https://view.vzaar.com/subtitles/123"
                }
            }
       '''

        when:
        Lookup<Subtitle> entity = mapper.readValue(payload, new TypeReference<Lookup<Subtitle>>() {});

        then:
        with(entity.data) {
            id == 123
            code == 'en'
            title == 'english-subtitles.srt'
            language == 'English'
            url == 'https://view.vzaar.com/subtitles/123'
            createdAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
        }
    }
}
