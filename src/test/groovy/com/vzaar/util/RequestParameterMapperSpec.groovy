package com.vzaar.util

import com.vzaar.VideoPageRequest
import com.vzaar.VideoState
import spock.lang.Specification

class RequestParameterMapperSpec extends Specification {

    private final RequestParameterMapper mapper = new RequestParameterMapper();

    def "I can get a map of the request parameters"() {
        given:
        VideoPageRequest request = new VideoPageRequest()
                .withQuery("query")
                .withResultsPerPage(5)
                .withState(VideoState.failed)
        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['q'] == "query"
        parameters['per_page'] == "5"
        parameters['state'] == "failed"
        parameters.size() == 3
    }

    def "I can write to query parameters"() {
        given:
        VideoPageRequest request = new VideoPageRequest()
                .withQuery("query")
                .withResultsPerPage(5)
                .withState(VideoState.failed)
        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?q=query&state=failed&per_page=5"
    }

    def "Query parameters is empty string if no values are set"() {
        given:
        VideoPageRequest request = new VideoPageRequest()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/videos?q=query&state=failed&per_page=5&nonexisting=false"

        when:
        VideoPageRequest request = mapper.read(new URL(query), VideoPageRequest);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['q'] == "query"
        parameters['per_page'] == "5"
        parameters['state'] == "failed"
        parameters.size() == 3
    }

    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/videos?q=query&state=failed&per_page=5&nonexisting=false"

        when:
        VideoPageRequest request = mapper.read(new URL(query), VideoPageRequest);
        request.withState(VideoState.ready)
        request.withResultsPerPage(4)
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['q'] == "query"
        parameters['per_page'] == "4"
        parameters['state'] == "ready"
        parameters.size() == 3
    }
}
