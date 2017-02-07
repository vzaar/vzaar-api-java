package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class IngestRecipeCreateRequestSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        IngestRecipeCreateRequest request = new IngestRecipeCreateRequest()
                .withDefault(true)
                .withDescription("Recipe description")
                .withEncodingPresetIds([2, 3] as Set)
                .withGenerateAnimatedThumb(true)
                .withGenerateSprite(true)
                .withMultipass(true)
                .withName("new_video")
                .withSendToYoutube(true)
                .withUseWatermark(true)
                .withNotifyByEmail(true)
                .withNotifyByPingback(true)

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("default") == true
        result.get("description") == 'Recipe description'
        result.get("encoding_preset_ids") as Set == [2, 3] as Set
        result.get("generate_animated_thumb") == true
        result.get("generate_sprite") == true
        result.get("multipass") == true
        result.get("name") == "new_video"
        result.get("send_to_youtube") == true
        result.get("use_watermark") == true
        result.get("notify_by_email") == true
        result.get("notify_by_pingback") == true
    }

    def "I can populate the request with an entity"() {
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
                "send_to_youtube": true,
                "notify_by_email": true,
                "notify_by_pingback": true,
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
        Lookup<IngestRecipe> entity = mapper.readValue(payload, new TypeReference<Lookup<IngestRecipe>>() {});

        when:
        IngestRecipeCreateRequest request = new IngestRecipeCreateRequest()
                .withIngestRecipe(entity.data)


        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("default") == true
        result.get("description") == 'Recipe description'
        result.get("encoding_preset_ids") as Set == [4, 5] as Set
        result.get("generate_animated_thumb") == true
        result.get("generate_sprite") == true
        result.get("multipass") == true
        result.get("name") == "Ingest recipes 1"
        result.get("send_to_youtube") == true
        result.get("use_watermark") == true
        result.get("notify_by_email") == true
        result.get("notify_by_pingback") == true
    }
}
