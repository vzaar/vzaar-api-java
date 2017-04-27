package com.vzaar

import spock.lang.Unroll

class PlaylistIntegrationSpec extends BaseIntegrationSpec {

    private static List<Playlist> playlists
    private static Playlist p1
    private static Playlist p2
    private static Playlist p3
    private static Category c1
    private static Category c2


    def setupSpec() {
        c1 = vzaar.categories().create().withName("Category 1").result()
        c2 = vzaar.categories().create().withName("Category 2").result()
        p1 = vzaar.playlists().create()
                .withTitle("Playlist 1")
                .withCategoryId(c1.id)
                .withSortOrder(SortDirection.asc)
                .withSortBy("created_at")
                .withMaxVids(41)
                .withPosition(ControlsPosition.top)
                .withPrivate(true)
                .withAutoplay(true)
                .withContinuousPlay(true)
                .withDimensions('640x480')
                .result()
        p2 = vzaar.playlists().create()
                .withTitle("Playlist 2")
                .withCategoryId(c2.id)
                .withSortOrder(SortDirection.desc)
                .withSortBy("title")
                .withMaxVids(42)
                .withPosition(ControlsPosition.bottom)
                .withPrivate(false)
                .withAutoplay(true)
                .withContinuousPlay(false)
                .withDimensions('650x490')
                .result()
        p3 = vzaar.playlists().create()
                .withTitle("Playlist 3")
                .withCategoryId(c1.id)
                .withSortOrder(SortDirection.asc)
                .withSortBy("title")
                .withMaxVids(43)
                .withPosition(ControlsPosition.left)
                .withPrivate(false)
                .withAutoplay(false)
                .withContinuousPlay(true)
                .withDimensions('auto')
                .result()
        playlists = [p1, p2, p3]
    }

    @Unroll("I can see the data from the server is correct #title")
    def "I can see the data from the server is correct"() {
        when:
        Playlist entity = vzaar.playlists().get(playlist.id)

        then:
        entity.id == playlist.id
        entity.categoryId == categoryId
        entity.title == title
        entity.sortOrder == sortOrder
        entity.sortBy == sortBy
        entity.maxVids == maxVids
        entity.position == position
        entity.private == isPrivate
        entity.dimensions == dimensions
        entity.autoplay == autoplay
        entity.continuousPlay == continuousPlay


        where:
        playlist | title        | categoryId | sortOrder          | sortBy       | maxVids | position                | isPrivate | dimensions | autoplay | continuousPlay
        p1       | 'Playlist 1' | c1.id      | SortDirection.asc  | "created_at" | 41      | ControlsPosition.top    | true      | '640x480'  | true     | true
        p2       | 'Playlist 2' | c2.id      | SortDirection.desc | "title"      | 42      | ControlsPosition.bottom | false     | '650x490'  | true     | false
        p3       | 'Playlist 3' | c1.id      | SortDirection.asc  | "title"      | 43      | ControlsPosition.left   | false     | 'auto'     | false    | true
    }

    def "I can list playlists"() {
        when:
        Page<Playlist> page = vzaar.playlists().list().results();

        then:
        page.totalCount >= 3
        page.data.size() >= 3
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()
    }

    def "I can page playlists"() {
        given:
        List<Playlist> playlists = Pages.list(vzaar.playlists().list().results())
        PlaylistPageRequest request = vzaar.playlists().list().withResultsPerPage(1)

        when:
        Page<Playlist> page1 = request.results()

        then:
        page1.totalCount >= 3
        page1.data.size() == 1
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        playlists[0].id == page1.data[0].id

        when:
        Page<Playlist> last = page1.last

        then:
        last.totalCount >= 3
        last.data.size() == 1
        !last.hasNext()
        last.hasPrevious()
        last.hasLast()
        playlists.last().id == last.data[0].id

        when:
        Page<Playlist> page2 = last.previous

        then:
        page2.totalCount >= 3
        page2.data.size() == 1
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        playlists[playlists.size() - 2].id == page2.data[0].id
    }

    @Unroll("I can sort playlists by #attribute")
    def "I can sort playlists by attributes"() {
        given:
        PlaylistPageRequest request = vzaar.playlists().list()
                .withResultsPerPage(2)
                .withSortByAttribute(attribute)
                .withSortDirection(SortDirection.asc)

        when:
        List<Playlist> playlists = Pages.list(request.results())

        then:
        playlists.size() > 0
        playlists.collect(map) == playlists.collect(map).sort()

        when:
        playlists = Pages.list(request.withSortDirection(SortDirection.desc).results())

        then:
        playlists.size() > 0
        playlists.collect(map) == playlists.collect(map).sort().reverse()

        where:
        attribute    | map
        'created_at' | { it.createdAt }
        'title'      | { it.title }
    }

    def "I can update a playlist"() {
        when:
        Playlist entity = vzaar.playlists().create()
                .withTitle("Updatable Playlist")
                .withCategoryId(c1.id)
                .withSortOrder(SortDirection.asc)
                .withSortBy("created_at")
                .withMaxVids(41)
                .withPosition(ControlsPosition.top)
                .withPrivate(true)
                .withAutoplay(true)
                .withContinuousPlay(true)
                .withDimensions('640x480')
                .result()
        then:
        entity.title == 'Updatable Playlist'
        entity.categoryId == c1.id
        entity.sortOrder == SortDirection.asc
        entity.sortBy == 'created_at'
        entity.maxVids == 41
        entity.position == ControlsPosition.top
        entity.private
        entity.dimensions == '640x480'
        entity.autoplay
        entity.continuousPlay

        when:
        entity = vzaar.playlists().update(entity.id)
                .withTitle("Updated Playlist")
                .withCategoryId(c2.id)
                .withSortOrder(SortDirection.desc)
                .withSortBy("title")
                .withMaxVids(21)
                .withPosition(ControlsPosition.bottom)
                .withPrivate(false)
                .withAutoplay(false)
                .withContinuousPlay(false)
                .withDimensions('1280x960')
                .result()

        then:
        entity.title == 'Updated Playlist'
        entity.categoryId == c2.id
        entity.sortOrder == SortDirection.desc
        entity.sortBy == 'title'
        entity.maxVids == 21
        entity.position == ControlsPosition.bottom
        !entity.private
        entity.dimensions == '1280x960'
        !entity.autoplay
        !entity.continuousPlay

        cleanup:
        vzaar.playlists().delete(entity.id)
    }

    def "I can delete a playlist"() {
        given:
        Playlist playlist = vzaar.playlists().create()
                .withTitle("Deletable Playlist")
                .withCategoryId(c2.id)
                .result()

        when:
        vzaar.playlists().delete(playlist.id)
        vzaar.playlists().get(playlist.id)

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.statusCode == 404
    }
}
