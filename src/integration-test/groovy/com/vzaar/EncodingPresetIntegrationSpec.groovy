package com.vzaar

import spock.lang.Unroll

public class EncodingPresetIntegrationSpec extends BaseIntegrationSpec {

    def "I can get encoding presets"() {
        when:
        Page<EncodingPreset> page = vzaar.encodingPresets(new EncodingPresetPageRequest())

        then:
        page.totalCount >= 3
        page.data.size() >= 3
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
    }

    def "I can get an encoding preset"() {
        given:
        Page<EncodingPreset> page = vzaar.encodingPresets(new EncodingPresetPageRequest().withResultsPerPage(1))

        when:
        EncodingPreset preset = vzaar.encodingPreset(page.data[0].id)

        then:
        preset.id == page.data[0].id
        preset.name == page.data[0].name
    }

    def "I can paginate encoding presets"() {
        given:
        List<EncodingPreset> presets = Pages.list(vzaar.encodingPresets(new EncodingPresetPageRequest()))
        EncodingPresetPageRequest request = new EncodingPresetPageRequest().withResultsPerPage(1)

        when:
        Page<EncodingPreset> page1 = vzaar.encodingPresets(request)

        then:
        page1.totalCount >= 3
        page1.data.size() == 1
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        presets[0].id == page1.data[0].id

        when:
        Page<EncodingPreset> page2 = page1.next

        then:
        page2.totalCount >= 3
        page2.data.size() == 1
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        presets[1].id == page2.data[0].id
    }

    @Unroll("I can sort presets by #attribute")
    def "I can sort presets by attributes"() {
        given:
        EncodingPresetPageRequest request = new EncodingPresetPageRequest().withResultsPerPage(2).withSortByAttribute(attribute).withSortDirection(SortDirection.asc)

        when:
        List<EncodingPreset> presets = Pages.list(vzaar.encodingPresets(request))

        then:
        presets.size() > 0
        presets.collect(map) == presets.collect(map).sort()

        when:
        presets = Pages.list(vzaar.encodingPresets(request.withSortDirection(SortDirection.desc)))

        then:
        presets.size() > 0
        presets.collect(map) == presets.collect(map).sort().reverse()

        where:
        attribute    | map
        'id'         | { it.id }
        'name'       | { it.name }
    }
}
