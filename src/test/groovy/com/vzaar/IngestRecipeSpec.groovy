package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class IngestRecipeSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "data": {
                "id": 1,
                "name": "Ingest recipes 1",
                "recipe_type": "new_video",
                "description": "Recipe description",
                "account_id": 10,
                "user_id": 42,
                "default": true,
                "multipass": true,
                "frame_grab_time": "3.5",
                "generate_animated_thumb": true,
                "generate_sprite": true,
                "use_watermark": true,
                "notify_by_email": true,
                "notify_by_pingback": true,
                "send_to_youtube": true,
                "encoding_presets": [
                  {
                    "id": 4,
                    "name": "LD",
                    "description": "Low Definition",
                    "output_format": "mp4",
                    "bitrate_kbps": 400,
                    "max_bitrate_kbps": 520,
                    "long_dimension": 480,
                    "video_codec": "libx264",
                    "profile": "main",
                    "frame_rate_upper_threshold": "29.97",
                    "audio_bitrate_kbps": 128,
                    "audio_channels": 2,
                    "audio_sample_rate": 44100,
                    "created_at": "2016-10-24T12:36:47.000Z",
                    "updated_at": "2016-10-24T12:36:47.000Z"
                  },
                  {
                    "id": 5,
                    "name": "SD - Lower",
                    "description": "Our lower Standard Definition rendition",
                    "output_format": "mp4",
                    "bitrate_kbps": 800,
                    "max_bitrate_kbps": 1040,
                    "long_dimension": 640,
                    "video_codec": "libx264",
                    "profile": "main",
                    "frame_rate_upper_threshold": "29.97",
                    "audio_bitrate_kbps": 128,
                    "audio_channels": 2,
                    "audio_sample_rate": 44100,
                    "created_at": "2016-10-24T12:36:47.000Z",
                    "updated_at": "2016-10-24T12:36:47.000Z"
                  }
                ],
                "created_at": "2016-10-26T11:00:55.000Z",
                "updated_at": "2016-10-26T11:00:56.000Z"
              }
            }
       '''

        when:
        Lookup<IngestRecipe> entity = mapper.readValue(payload, new TypeReference<Lookup<IngestRecipe>>() {});

        then:
        with(entity.data) {
            id == 1
            name == 'Ingest recipes 1'
            recipeType == 'new_video'
            description == 'Recipe description'
            accountId == 10
            userId == 42
            isDefault()
            multipass
            frameGrabTime == '3.5'
            generateAnimatedThumb
            generateSprite
            useWatermark
            sendToYoutube
            notifyByEmail
            notifyByPingback
            encodingPresets.size() == 2
            encodingPresetsIds == [4, 5] as Set
            createdAt.isEqual(ZonedDateTime.parse("2016-10-26T11:00:55.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2016-10-26T11:00:56.000Z"))
        }
        with(entity.data.encodingPresets[1]) {
            id == 5
            name == 'SD - Lower'
            description == 'Our lower Standard Definition rendition'
            outputFormat == 'mp4'
            bitrateKbps == 800
            maxBitrateKbps == 1040
            longDimension == 640
            videoCodec == 'libx264'
            profile == 'main'
            frameRateUpperThreshold == '29.97'
            audioBitrateKbps == 128
            audioChannels == 2
            audioSampleRate == 44100
            createdAt.isEqual(ZonedDateTime.parse("2016-10-24T12:36:47.000Z"))
            updatedAt.isEqual(ZonedDateTime.parse("2016-10-24T12:36:47.000Z"))
        }
    }
}
