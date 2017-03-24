package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class PlaylistUpdateRequestSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        PlaylistUpdateRequest request = new PlaylistUpdateRequest(null)
                .withTitle("Playlist 1")
                .withCategoryId(42)
                .withSortOrder(SortDirection.asc)
                .withSortBy("created_at")
                .withMaxVids(41)
                .withPosition(ControlsPosition.top)
                .withPrivate(true)
                .withAutoplay(true)
                .withContinuousPlay(true)
                .withDimensions('640x480')

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("title") == 'Playlist 1'
        result.get("category_id") == 42
        result.get("sort_order") == 'asc'
        result.get("sort_by") == 'created_at'
        result.get("max_vids") == 41
        result.get("position") == 'top'
        result.get("private") == true
        result.get("autoplay") == true
        result.get("continuous_play") == true
        result.get("dimensions") == '640x480'
    }
}
