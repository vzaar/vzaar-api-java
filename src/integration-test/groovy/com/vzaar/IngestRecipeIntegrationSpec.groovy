package com.vzaar

import spock.lang.Unroll

class IngestRecipeIntegrationSpec extends BaseIntegrationSpec {

    private static List<EncodingPreset> encodingPresets;
    private static IngestRecipe defaultRecipe
    private static IngestRecipe ir1
    private static IngestRecipe ir2
    private static IngestRecipe ir3

    private static List<IngestRecipe> recipes

    def setupSpec() {
        encodingPresets = Pages.list(vzaar.encodingPresets().list().results())
        defaultRecipe = Pages.list(vzaar.recipes().list().results()).find { it.default }
        ir1 = vzaar.recipes().create()
                .withName("Recipe 1")
                .withDescription("Recipe 1 Description")
                .withEncodingPresetIds([encodingPresets[0].id] as Set)
                .withGenerateAnimatedThumb(false)
                .withGenerateSprite(false)
                .withMultipass(false)
                .withSendToYoutube(false)
                .withUseWatermark(false)
                .result()
        ir2 = vzaar.recipes().create()
                .withName("Recipe 2")
                .withDescription("Recipe 2 Description")
                .withEncodingPresetIds([encodingPresets[0].id, encodingPresets[1].id] as Set)
                .withGenerateAnimatedThumb(true)
                .withGenerateSprite(true)
                .withMultipass(true)
                .withSendToYoutube(true)
                .withUseWatermark(true)
                .result()
        ir3 = vzaar.recipes().create()
                .withName("Recipe 3")
                .withDescription("Recipe 3 Description")
                .withEncodingPresetIds([encodingPresets[1].id] as Set)
                .result()
        recipes = [ir1, ir2, ir3]
    }

    @Unroll("I can see the data from the server is correct #name")
    def "I can see the data from the server is correct"() {
        when:
        IngestRecipe recipe = vzaar.recipes().get(category.id)

        then:
        recipe.name == name
        recipe.description == "${name} Description".toString()
        recipe.encodingPresets.id as Set == presets as Set
        recipe.generateAnimatedThumb == booleanValues
        recipe.generateSprite == booleanValues
        recipe.multipass == booleanValues
        recipe.sendToYoutube == booleanValues
        recipe.useWatermark == booleanValues

        where:
        category | name       | booleanValues | presets
        ir1      | 'Recipe 1' | false         | [encodingPresets[0].id]
        ir2      | 'Recipe 2' | true          | [encodingPresets[0].id, encodingPresets[1].id]
    }

    def "I can list recipes"() {
        when:
        Page<IngestRecipe> page = vzaar.recipes().list().results();

        then:
        page.totalCount >= 3
        page.data.size() >= 3
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
    }

    def "I can page recipes"() {
        given:
        List<IngestRecipe> recipes = Pages.list(vzaar.recipes().list().results())
        IngestRecipePageRequest request = vzaar.recipes().list().withResultsPerPage(1)

        when:
        Page<IngestRecipe> page1 = request.results()

        then:
        page1.totalCount >= 3
        page1.data.size() == 1
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        recipes[0].id == page1.data[0].id

        when:
        Page<IngestRecipe> last = page1.last

        then:
        last.totalCount >= 3
        last.data.size() == 1
        !last.hasNext()
        last.hasPrevious()
        last.hasLast()
        recipes.last().id == last.data[0].id

        when:
        Page<IngestRecipe> page2 = last.previous

        then:
        page2.totalCount >= 3
        page2.data.size() == 1
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        recipes[recipes.size() - 2].id == page2.data[0].id
    }

    @Unroll("I can sort recipes by #attribute")
    def "I can sort recipes by attributes"() {
        given:
        IngestRecipePageRequest request = vzaar.recipes().list()
                .withResultsPerPage(2)
                .withSortByAttribute(attribute)
                .withSortDirection(SortDirection.asc)

        when:
        List<IngestRecipe> recipes = Pages.list(request.results())

        then:
        recipes.size() > 0
        recipes.collect(map) == recipes.collect(map).sort()

        when:
        recipes = Pages.list(request.withSortDirection(SortDirection.desc).results())

        then:
        recipes.size() > 0
        recipes.collect(map) == recipes.collect(map).sort().reverse()

        where:
        attribute | map
        'id'      | { it.id }
        'name'    | { it.name }
    }

    def "I can update a category"() {
        when:
        IngestRecipe recipe = vzaar.recipes().create()
                .withName("Updatable Recipe")
                .withDescription("Updatable Recipe Description")
                .withGenerateAnimatedThumb(true)
                .withUseWatermark(true)
                .withSendToYoutube(true)
                .withMultipass(true)
                .withGenerateSprite(true)
                .withDefault(true)
                .withEncodingPresetIds([encodingPresets[1].id] as Set)
                .result()
        then:
        recipe.name == 'Updatable Recipe'
        recipe.description == 'Updatable Recipe Description'
        recipe.encodingPresets.id as Set == [encodingPresets[1].id] as Set
        recipe.generateAnimatedThumb
        recipe.generateSprite
        recipe.multipass
        recipe.sendToYoutube
        recipe.useWatermark
        recipe.default

        when:
        vzaar.recipes().update(defaultRecipe.id).withDefault(true).result();
        recipe = vzaar.recipes().update(recipe.id)
                .withName("Updated Recipe")
                .withDescription("Updated Recipe Description")
                .withGenerateAnimatedThumb(false)
                .withUseWatermark(false)
                .withSendToYoutube(false)
                .withMultipass(false)
                .withGenerateSprite(false)
                .withEncodingPresetIds([encodingPresets[0].id, encodingPresets[1].id] as Set)
                .result()

        then:
        recipe.name == 'Updated Recipe'
        recipe.description == 'Updated Recipe Description'
        recipe.encodingPresets.id as Set == [encodingPresets[0].id, encodingPresets[1].id] as Set
        !recipe.generateAnimatedThumb
        !recipe.generateSprite
        !recipe.multipass
        !recipe.sendToYoutube
        !recipe.useWatermark
        !recipe.default

        cleanup:
        vzaar.recipes().update(defaultRecipe.id).withDefault(true).result()
        vzaar.recipes().delete(recipe.id)
    }

    def "I can delete a recipe"() {
        given:
        IngestRecipe recipe = vzaar.recipes().create()
                .withName("Deletable Recipe")
                .withEncodingPresetIds([encodingPresets[1].id] as Set)
                .result()

        when:
        vzaar.recipes().delete(recipe.id)
        vzaar.recipes().get(recipe.id)

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.statusCode == 404
    }
}
