package com.vzaar

import com.vzaar.util.RequestParameterMapper
import spock.lang.Specification

class IngestRecipePageRequestSpec extends Specification {
    private final RequestParameterMapper mapper = new RequestParameterMapper();

    def "I can get a map of the request parameters"() {
        given:
        IngestRecipePageRequest request = new IngestRecipePageRequest()
                .withResultsPerPage(5)
                .withPage(2)
                .withSortByAttribute("name")
                .withSortDirection(SortDirection.desc)
        when:
        Map<String, String> parameters = mapper.writeToMap(request)

        then:
        parameters['page'] == "2"
        parameters['per_page'] == "5"
        parameters['sort'] == "name"
        parameters['order'] == "desc"
        parameters.size() == 4
    }

    def "I can write to query parameters"() {
        given:
        IngestRecipePageRequest request = new IngestRecipePageRequest()
                .withResultsPerPage(5)
                .withPage(2)
                .withSortByAttribute("name")
                .withSortDirection(SortDirection.desc)

        when:
        String query = new RequestParameterMapper().write(request)

        then:
        query == "?sort=name&order=desc&page=2&per_page=5"
    }

    def "Query parameters is empty string if no values are set"() {
        given:
        IngestRecipePageRequest request = new IngestRecipePageRequest()

        when:
        String query = mapper.write(request)

        then:
        query == ""
    }

    def "I can convert a query string back to a request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/encoding_presets?page=2&per_page=5&sort=name&order=desc"

        when:
        IngestRecipePageRequest request = mapper.read(new URL(query), IngestRecipePageRequest);
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['page'] == "2"
        parameters['per_page'] == "5"
        parameters['sort'] == "name"
        parameters['order'] == "desc"
        parameters.size() == 4
    }

    def "I can modify a mapped request object"() {
        given:
        String query = "https://api.vzaar.com/api/v2/ingest_recipes?page=2&per_page=5&sort=name&order=desc"

        when:
        IngestRecipePageRequest request = mapper.read(new URL(query), IngestRecipePageRequest);
        request.withResultsPerPage(4)
                .withPage(1)
                .withSortByAttribute("id")
                .withSortDirection(SortDirection.asc)
        Map<String, String> parameters = new RequestParameterMapper().writeToMap(request)

        then:
        parameters['page'] == "1"
        parameters['per_page'] == "4"
        parameters['sort'] == "id"
        parameters['order'] == "asc"
        parameters.size() == 4
    }
}
