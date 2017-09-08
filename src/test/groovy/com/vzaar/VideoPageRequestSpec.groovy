package com.vzaar

import com.vzaar.util.RequestParameterMapper
import spock.lang.Specification

class VideoPageRequestSpec extends Specification {
    private final RequestParameterMapper mapper = new RequestParameterMapper();

    def "I can get a map of the request parameters"() {
        given:
        VideoPageRequest request = new VideoPageRequest()
                .withEscapedQuery("my_query")
                .withState(VideoState.failed)
                .withIsCategorised(true)
                .withCategoryId(1)
                .withResultsPerPage(5)
        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['q'] == "my_query"
        parameters['state'] == "failed"
        parameters['category_id'] == "1"
        parameters['per_page'] == "5"
        parameters['categorised'] == "true"
        parameters.size() == 5
    }

    def "I can write to query parameters"() {
        given:
        VideoPageRequest request = new VideoPageRequest()
                .withQuery("my_query")
                .withState(VideoState.failed)
                .withCategoryId(1)
                .withIsCategorised(true)
                .withResultsPerPage(5)

        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?q=my_query&category_id=1&categorised=true&state=failed&per_page=5"
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
        String query = "https://api.vzaar.com/api/v2/videos?q=my_query&state=failed&category_id=1&per_page=5"

        when:
        VideoPageRequest request = mapper.read(new URL(query), VideoPageRequest);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['q'] == "my_query"
        parameters['state'] == "failed"
        parameters['category_id'] == "1"
        parameters['per_page'] == "5"
        parameters.size() == 4
    }

    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/categories?q=my_query&state=failed&category_id=1&per_page=5"

        when:
        VideoPageRequest request = mapper.read(new URL(query), VideoPageRequest);
        request.withQuery("another_query")
        request.withCategoryId(7)
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['q'] == "another_query"
        parameters['category_id'] == "7"
        parameters['per_page'] == "5"
        parameters['state'] == "failed"
        parameters.size() == 4
    }}
