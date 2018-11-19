package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class VideoSpec extends Specification {

    private ObjectMapper mapper

    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        mapper = ObjectMapperFactory.make()
    }

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "data": {
                "id": 7574853,
                "title": "multipart",
                "user_id": 42,
                "user_login": "my-login",
                "account_id": 1,
                "account_name": "my-account",
                "description": "My video",
                "duration": 66.7,
                "created_at": "2016-11-11T11:36:26.000Z",
                "updated_at": "2016-11-11T11:37:36.000Z",
                "private": true,
                "seo_url": "http://example.com/video.mp4",
                "url": "http://example.com/video1.mp4",
                "state": "ready",
                "asset_url": "https://view.vzaar.com/123/video",
                "poster_url": "https://view.vzaar.com/123/image",
                "thumbnail_url": "https://view.vzaar.com/7574853/thumb",
                "embed_code": "<iframe id=\\"vzvd-7574853\\" name=\\"vzvd-7574853\\" title=\\"video player\\" class=\\"video-player\\" type=\\"text/html\\" width=\\"448\\" height=\\"278\\" frameborder=\\"0\\" allowfullscreen allowTransparency=\\"true\\" mozallowfullscreen webkitAllowFullScreen src=\\"//view.vzaar.com/7574853/player\\"></iframe>",
                "renditions": [
                  {
                    "id": 66,
                    "code": "magic",
                    "width": 416,
                    "height": 258,
                    "bitrate": 200,
                    "framerate": "12.0",
                    "status": "finished",
                    "size_in_bytes": 12345
                  }
                ],
                "legacy_renditions": [
                  {
                    "id": 10567122,
                    "type": "standard",
                    "width": 448,
                    "height": 278,
                    "bitrate": 512,
                    "status": "Finished"
                  }
                ]
              }
            }
       '''

        when:
        Lookup<Video> entity = mapper.readValue(payload, new TypeReference<Lookup<Video>>() {});

        then:
        with(entity.data) {
            id == 7574853
            title == 'multipart'
            userId == 42
            userLogin == 'my-login'
            accountId == 1
            accountName == 'my-account'
            description == 'My video'
            duration == 66.7 as Double
            createdAt.isEqual(ZonedDateTime.parse("2016-11-11T11:36:26.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2016-11-11T11:37:36.000Z"))
            isPrivate()
            seoUrl == 'http://example.com/video.mp4'
            url == 'http://example.com/video1.mp4'
            state == VideoState.ready
            assetUrl == 'https://view.vzaar.com/123/video'
            posterUrl == 'https://view.vzaar.com/123/image'
            thumbnailUrl == 'https://view.vzaar.com/7574853/thumb'
            embedCode == '<iframe id="vzvd-7574853" name="vzvd-7574853" title="video player" class="video-player" type="text/html" width="448" height="278" frameborder="0" allowfullscreen allowTransparency="true" mozallowfullscreen webkitAllowFullScreen src="//view.vzaar.com/7574853/player"></iframe>'
            renditions.size() == 1
            legacyRenditions.size() == 1
        }
        with(entity.data.renditions[0]) {
            id == 66
            code == 'magic'
            width == 416
            height == 258
            bitrate == 200
            framerate == '12.0'
            status == 'finished'
            sizeInBytes == 12345L
        }
        with(entity.data.legacyRenditions[0]) {
            id == 10567122
            type == "standard"
            width == 448
            height == 278
            bitrate == 512
            status == 'Finished'
        }
    }
}
