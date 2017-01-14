package com.vzaar.util

import com.vzaar.VideoListRequest
import com.vzaar.VideoState
import spock.lang.Specification

class RequestParameterMapperSpec extends Specification {

    private final RequestParameterMapper mapper = new RequestParameterMapper();

    def "I can get a map of the request parameters"() {
        given:
        VideoListRequest request = new VideoListRequest()
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
        VideoListRequest request = new VideoListRequest()
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
        VideoListRequest request = new VideoListRequest()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/videos?q=query&state=failed&per_page=5&nonexisting=false"

        when:
        VideoListRequest request = mapper.read(new URL(query), VideoListRequest);
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
        VideoListRequest request = mapper.read(new URL(query), VideoListRequest);
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
