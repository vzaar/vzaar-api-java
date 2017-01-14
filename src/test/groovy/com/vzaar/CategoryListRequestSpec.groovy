package com.vzaar

import com.vzaar.util.RequestParameterMapper
import spock.lang.Specification

class CategoryListRequestSpec extends Specification {

    private final RequestParameterMapper mapper = new RequestParameterMapper();

    def "I can get a map of the request parameters"() {
        given:
        CategoryListRequest request = new CategoryListRequest()
                .withLevels(2)
                .withIds([10, 11, 12])
                .withResultsPerPage(5)
        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['levels'] == "2"
        parameters['per_page'] == "5"
        parameters['ids'] == "10,11,12"
        parameters.size() == 3
    }

    def "I can write to query parameters"() {
        given:
        CategoryListRequest request = new CategoryListRequest()
                .withLevels(2)
                .withIds([10, 11, 12])
                .withResultsPerPage(5)

        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?levels=2&ids=10%2C11%2C12&per_page=5"
    }

    def "Query parameters is empty string if no values are set"() {
        given:
        CategoryListRequest request = new CategoryListRequest()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/categories?levels=2&ids=10%2C11%2C12&per_page=5"

        when:
        CategoryListRequest request = mapper.read(new URL(query), CategoryListRequest);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['levels'] == "2"
        parameters['per_page'] == "5"
        parameters['ids'] == "10,11,12"
        parameters.size() == 3
    }

    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/categories?levels=2&ids=10%2C11%2C12&per_page=5"

        when:
        CategoryListRequest request = mapper.read(new URL(query), CategoryListRequest);
        request.withLevels(5)
        request.withIds([55])
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['levels'] == "5"
        parameters['per_page'] == "5"
        parameters['ids'] == "55"
        parameters.size() == 3
    }
}
