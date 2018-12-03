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
                "categories": [
                  {
                    "id": 1,
                    "account_id": 1,
                    "user_id": 1,
                    "name": "Sciences",
                    "description": null,
                    "parent_id": null,
                    "depth": 0,
                    "node_children_count": 3,
                    "tree_children_count": 5,
                    "node_video_count": 3,
                    "tree_video_count": 6,
                    "created_at": "2015-04-06T22:03:24.000Z",
                    "updated_at": "2016-01-06T12:08:38.000Z"
                  }
                ],
                "adverts": [
                     {
                       "advert_id": 1,
                       "name": "Single skippable inline (test)",
                       "tag": "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator=",
                       "position": "mid",
                       "time": "00:00:30",
                       "placement_scope": "user",
                       "created_at": "2017-09-06T21:20:27.000Z",
                       "updated_at": "2017-09-06T21:20:27.000Z"
                     }
                 ],
                "subtitles": [
                    {
                        "id": 123,
                        "code": "en",
                        "title": "english-subtitles.srt",
                        "language": "English",
                        "created_at": "2017-09-06T21:20:27.000Z",
                        "updated_at": "2017-09-06T21:20:27.000Z",
                        "url": "https://view.vzaar.com/subtitles/123"
                    }
                ],
                "renditions": [
                  {
                    "id": 66,
                    "code": "magic",
                    "width": 416,
                    "height": 258,
                    "bitrate": 200,
                    "framerate": "12.0",
                    "status": "finished",
                    "size_in_bytes": 12345,
                    "url": "https://view.vzaar.com/123/download/magic"
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
            adverts.size() == 1
            subtitles.size() == 1
            categories.size() == 1
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
            url == 'https://view.vzaar.com/123/download/magic'
        }
        with(entity.data.legacyRenditions[0]) {
            id == 10567122
            type == "standard"
            width == 448
            height == 278
            bitrate == 512
            status == 'Finished'
        }
        with(entity.data.adverts[0]) {
            advertId == 1
            name == 'Single skippable inline (test)'
            tag == 'https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator='
            position == 'mid'
            time == '00:00:30'
            placementScope == 'user'
            createdAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
        }
        with(entity.data.subtitles[0]) {
            id == 123
            code == 'en'
            title == 'english-subtitles.srt'
            language == 'English'
            url == 'https://view.vzaar.com/subtitles/123'
            createdAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
        }
    }
}
