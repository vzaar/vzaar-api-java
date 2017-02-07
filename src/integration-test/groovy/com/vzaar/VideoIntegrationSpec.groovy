package com.vzaar

import spock.lang.Unroll

public class VideoIntegrationSpec extends BaseIntegrationSpec {

    def setup() {
        // Wait for list stability
        while (vzaar.videos().list().withState(VideoState.processing).results().totalCount > 0) {
            try {
                sleep(10000)
            } catch (Exception ignore) {
            }
        }
    }

    def "I can get videos"() {
        when:
        Page<Video> page = vzaar.videos().list().results()

        then:
        page.totalCount >= 3
        page.data.size() >= 3
        !page.hasNext()
        !page.hasPrevious()
        !page.hasLast()

        page.data.state as Set == [VideoState.ready] as Set
    }

    def "I can paginate videos"() {
        given:
        List<Video> videos = Pages.list(vzaar.videos().list().results())
        VideoPageRequest request = vzaar.videos().list().withResultsPerPage(1)

        when:
        Page<Video> page1 = request.results()

        then:
        page1.totalCount >= 3
        page1.data.size() == 1
        page1.hasNext()
        !page1.hasPrevious()
        page1.hasLast()
        videos[0].id == page1.data[0].id

        when:
        Page<Video> page2 = request.withPage(2).results()

        then:
        page2.totalCount >= 3
        page2.data.size() == 1
        page2.hasNext()
        page2.hasPrevious()
        page2.hasLast()
        videos[1].id == page2.data[0].id

        when:
        Page<Video> page3 = request.withPage(3).results()

        then:
        page3.totalCount >= 3
        page3.data.size() == 1
        !page3.hasNext()
        page3.hasPrevious()
        page3.hasLast()
        videos[2].id == page3.data[0].id
    }

    @Unroll("I can sort videos by #attribute")
    def "I can sort videos by attributes"() {
        given:
        VideoPageRequest request = vzaar.videos().list()
                .withResultsPerPage(2)
                .withSortByAttribute(attribute)
                .withSortDirection(SortDirection.asc)

        when:
        List<Video> videos = Pages.list(request.results())

        then:
        videos.size() > 0
        videos.collect(map) == videos.collect(map).sort()

        when:
        videos = Pages.list(request.withSortDirection(SortDirection.desc).results())

        then:
        videos.size() > 0
        videos.collect(map) == videos.collect(map).sort().reverse()

        where:
        attribute    | map
        'id'         | { it.id }
        'title'      | { it.title }
        'created_at' | { it.createdAt }
    }

    @Unroll("I can search for a video with special characters #search without getting an error")
    def "I can search for a video by title with special characters"() {
        when:
        Page<Video> page = vzaar.videos().list().withEscapedQuery(q).results()

        then:
        page.totalCount == 0

        where:
        search                | q
        'Square brackets'     | '[QUERY]'
        'Curly brackets'      | '{QUERY}'
        'Parentheses'         | '(QUERY)'
        'Double Double Quote' | '"QUERY"'
        'Single Double Quote' | '"QUERY'
        'Single Single Quote' | '\''
        'Plus sign'           | '+QUERY+'
        'Question mark'       | '?QUERY?'
        'And'                 | 'QUERY&&QUERY'
        'Or'                  | 'QUERY||QUERY'
    }

    def "I can search for a video by title"() {
        given:
        Page<Video> allVideosPage = vzaar.videos().list().results()

        when:
        Page<Video> searchVideosPage = vzaar.videos().list().withEscapedQuery(allVideosPage.data[0].title).results()

        then:
        searchVideosPage.totalCount == 1
        allVideosPage.totalCount >= 3
        searchVideosPage.data[0].id == allVideosPage.data[0].id
    }


    def "I can update a video"() {
        given:
        List<IngestRecipe> recipes = Pages.list(vzaar.recipes().list().results())
        Category catUpload1 = vzaar.categories().create().withName("My Cat 8").result()
        List<Category> categories = [
                catUpload1,
                vzaar.categories().create()
                        .withName("My Cat 9")
                        .withParentId(catUpload1.id)
                        .result()
        ]

        when:
        Video video = vzaar.upload(new VideoUploadRequest()
                .withTitle("Updateable Video")
                .withDescription("Updateable video description")
                .withUploader("Integration test")
                .withIngestRecipeId(recipes[0].id)
                .withFile(smallVideo));

        then:
        video.title == 'Updateable Video'
        video.description == 'Updateable video description'
        video.seoUrl == null
        !video.private
        !video.categories

        when:
        Video waitForVideo
        while (waitForVideo == null || waitForVideo.state == VideoState.processing) {
            try {
                sleep(10000)
                waitForVideo = vzaar.videos().get(video.id)
            } catch (Exception ignore) {
            }
        }

        video = vzaar.videos().update(video.id)
                .withTitle("Updated Video")
                .withDescription("Updated video description")
                .withPrivate(true)
                .withSeoUrl("http://www.9ls.com/video.mp4")
                .withCategoryIds([categories[1].id] as Set)
                .result()

        then:
        video.title == 'Updated Video'
        video.description == 'Updated video description'
        video.seoUrl == 'http://www.9ls.com/video.mp4'
        video.private
        video.categories.id as Set == [categories[1].id] as Set

//        when:
//        Page<Video> page = vzaar.videos(new VideoPageRequest().withCategoryId(categories[0].id))
//
//        then:
//        page.totalCount == 1
//        page.data[0].id == video.id

        when:
        Category cat1 = vzaar.categories().get(categories[0].id)
        Category cat2 = vzaar.categories().get(categories[1].id)

        then:
        cat1.treeVideoCount == 1
        cat1.nodeVideoCount == 0
        cat2.treeVideoCount == 1
        cat2.nodeVideoCount == 1

        cleanup:
        vzaar.videos().delete(video.id)
        vzaar.categories().delete(categories[0].id)
    }

    def "I can upload a video via a link"() {
        when:
        Video video = vzaar.upload(new VideoLinkUploadRequest()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video"))

        then:
        video.id
        video.description == 'Link video description'
        video.title == 'Link video'
    }
}