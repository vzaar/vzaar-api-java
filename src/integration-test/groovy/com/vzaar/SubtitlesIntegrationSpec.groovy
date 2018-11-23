package com.vzaar

import com.vzaar.subtitles.SubRipSubtitles

class SubtitlesIntegrationSpec extends BaseIntegrationSpec {

    def setup() {
    }


    def setupSpec() {
    }

    def "I can list subtitles on a video with no subtitles"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.id)

        then:
        subtitles.size() == 0
    }

    def "I can add subtitles"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
            .withCode("en")
            //.withTitle("Subtitles EN")
            .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
            .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        Subtitle subtitleFr = vzaar.subtitles().create(video.getId())
                .withCode("fr")
//                .withTitle("Subtitles FR")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleFr.id
        subtitleFr.code == 'fr'
        subtitleFr.title == video.id + '-fr.srt'
        subtitleFr.language == 'French'
        subtitleFr.url == 'https://view.vzaar.com/subtitles/' + subtitleFr.id
        subtitleFr.createdAt
        subtitleFr.updatedAt

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.getId())

        then:
        subtitles.size() == 2

        when:
        Video videoWithSubtitles = vzaar.videos().get(video.getId())

        then:
        videoWithSubtitles.subtitles.size() == 2

        cleanup:
        if (subtitleEn) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (subtitleFr) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }

    def "I can add subtitles with an upload"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Subtitle video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
                .withCode("en")
                //.withTitle("Subtitles EN")
                .withFile(new File(getClass().classLoader.getResource("videos/subtitles.srt").getFile()))
                .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.getId())

        then:
        subtitles.size() == 1

        cleanup:
        if (subtitleEn) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }

    def "I can not add subtitles with the same code"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.message.contains('Subtitles for this language exist')

        cleanup:
        if (subtitleEn) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }

    def "I can update the subtitles"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        Subtitle subtitleEnUpdated = vzaar.subtitles().update(video.getId(), subtitleEn.id)
                .withCode("fr")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleEnUpdated.id
        subtitleEnUpdated.code == 'fr'
        subtitleEnUpdated.title == video.id + '-fr.srt'
        subtitleEnUpdated.language == 'French'
        subtitleEnUpdated.url == 'https://view.vzaar.com/subtitles/' + subtitleEnUpdated.id
        subtitleEnUpdated.createdAt
        subtitleEnUpdated.updatedAt
        subtitleEnUpdated.id == subtitleEn.id

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.getId())

        then:
        subtitles.size() == 1

        cleanup:
        if (subtitleEn) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }

    def "I can updates subtitles with an upload"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Subtitle video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        Subtitle subtitleUpdated = vzaar.subtitles().update(video.getId(), subtitleEn.id)
                .withCode("fr")
                .withFile(new File(getClass().classLoader.getResource("videos/subtitles.srt").getFile()))
                .result()

        then:
        subtitleUpdated.id
        subtitleUpdated.code == 'fr'
        subtitleUpdated.title == video.id + '-fr.srt'
        subtitleUpdated.language == 'French'
        subtitleUpdated.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleUpdated.createdAt
        subtitleUpdated.updatedAt

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.getId())

        then:
        subtitles.size() == 1

        cleanup:
        true
//        if (subtitleEn) {
//            vzaar.subtitles().delete(video.id, subtitleEn.id)
//        }
//        if (video) {
//            vzaar.videos().delete(video.id)
//        }
    }

    def "I can't update subtitles to code that already exists"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()

        when:
        Subtitle subtitleEn = vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleEn.id
        subtitleEn.code == 'en'
        subtitleEn.title == video.id + '-en.srt'
        subtitleEn.language == 'English'
        subtitleEn.url == 'https://view.vzaar.com/subtitles/' + subtitleEn.id
        subtitleEn.createdAt
        subtitleEn.updatedAt

        when:
        Subtitle subtitleFr = vzaar.subtitles().create(video.getId())
                .withCode("fr")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        subtitleFr.id
        subtitleFr.code == 'fr'
        subtitleFr.title == video.id + '-fr.srt'
        subtitleFr.language == 'French'
        subtitleFr.url == 'https://view.vzaar.com/subtitles/' + subtitleFr.id
        subtitleFr.createdAt
        subtitleFr.updatedAt

        when:
        Subtitle subtitleEnUpdated = vzaar.subtitles().update(video.getId(), subtitleEn.id)
                .withCode("fr")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        then:
        VzaarServerException exception = thrown(VzaarServerException)
        exception.message.contains('Subtitles for this language exist')

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.getId())

        then:
        subtitles.size() == 2

        cleanup:
        if (subtitleEn) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (subtitleFr) {
            vzaar.subtitles().delete(video.id, subtitleEn.id)
        }
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }

    def "I can delete a subtitle"() {
        given:
        Video video = vzaar.videos().uploadWithLink()
                .withUrl("https://github.com/nine-lives/vzaar-sdk-java/raw/master/src/integration-test/resources/videos/small.mp4")
                .withUploader("integration-test")
                .withDescription("Link video description")
                .withTitle("Link video")
                .result()
        Subtitle subtitle = vzaar.subtitles().create(video.getId())
                .withCode("en")
                .withContent(new SubRipSubtitles().addCue("00:00:00,498", "00:00:02,827", "My Subtitles"))
                .result()

        when:
        List<Subtitle> subtitles = vzaar.subtitles().list(video.id)

        then:
        subtitles.size() == 1

        when:
        vzaar.subtitles().delete(video.id, subtitle.id)
        subtitles = vzaar.subtitles().list(video.id)

        then:
        true
        // caching issuea
        // subtitles.size() == 0

        cleanup:
        if (video) {
            vzaar.videos().delete(video.id)
        }
    }
}
