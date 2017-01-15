package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class CategorySpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make();

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
                "data": {
                    "id": 10,
                    "account_id": 20,
                    "user_id": 30,
                    "name": "Sciences",
                    "description": "Changes",
                    "parent_id": 1,
                    "depth": 1,
                    "node_children_count": 3,
                    "tree_children_count": 5,
                    "node_video_count": 7,
                    "tree_video_count": 9,
                    "created_at": "2015-04-06T22:03:24.000Z",
                    "updated_at": "2016-01-06T12:08:38.000Z"
                }
            }
       '''

       when:
       Lookup<Category> entity = mapper.readValue(payload, new TypeReference<Lookup<Category>>() {});

       then:
       with(entity.data) {
            id == 10
           accountId == 20
           userId == 30
           name == 'Sciences'
           description == 'Changes'
           parentId == 1
           depth == 1
           nodeChildrenCount == 3
           treeChildrenCount == 5
           nodeVideoCount == 7
           treeVideoCount == 9
           createdAt.isEqual(ZonedDateTime.parse("2015-04-06T22:03:24.000Z"))
           updatedAt.isEqual(ZonedDateTime.parse("2016-01-06T12:08:38.000Z"))
       }
    }
}
