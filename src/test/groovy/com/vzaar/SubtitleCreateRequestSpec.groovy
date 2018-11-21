package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.subtitles.SubRipSubtitles
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class SubtitleCreateRequestSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        SubtitleCreateRequest request = new SubtitleCreateRequest(null)
                .withTitle("Subtitle 1")
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .withFile(File.createTempFile("vzaar", ".temp"))

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.size() == 3
        result.get("title") == 'Subtitle 1'
        result.get("code") == 'en'
        result.get("content") == '1\n00:00:00,498 --> 00:00:02,827\nMy Subtitles\n\n'
    }
}
