package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class EncodingPresetSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "data": {
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
            }
       '''

        when:
        Lookup<EncodingPreset> entity = mapper.readValue(payload, new TypeReference<Lookup<EncodingPreset>>() {});

        then:
        with(entity.data) {
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
