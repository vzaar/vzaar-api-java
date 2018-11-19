package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class AdvertSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "data": {
                   "advert_id": 1,
                   "name": "Single skippable inline (test)",
                   "tag": "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator=",
                   "position": "mid",
                   "time": "00:00:30",
                   "placement_scope": "user",
                   "created_at": "2017-09-06T21:20:27.000Z",
                   "updated_at": "2017-09-06T21:20:27.000Z"
                }
            }
       '''

       when:
       Lookup<Advert> entity = mapper.readValue(payload, new TypeReference<Lookup<Advert>>() {});

       then:
       with(entity.data) {
           advertId == 1
           name == 'Single skippable inline (test)'
           tag == 'https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dskippablelinear&correlator='
           position == 'mid'
           time == '00:00:30'
           placementScope == 'user'
           createdAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
           updatedAt.isEqual(ZonedDateTime.parse("2017-09-06T21:20:27.000Z"))
       }
    }
}
