package com.vzaar

class IngestRecipeIntegrationSpec extends BaseIntegrationSpec {

    def "I can list recipes"() {
        when:
        Page<IngestRecipe> page = vzaar.recipes(new IngestRecipePageRequest());

        then:
        with(page) {
            page.meta.totalCount == 2
        }
    }
}
