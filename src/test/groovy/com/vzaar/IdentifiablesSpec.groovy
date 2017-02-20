package com.vzaar

import spock.lang.Specification

class IdentifiablesSpec extends Specification {
    static class Entity implements Identifiable {
        int id;
    }

    private List<Entity> entities = [
            new Entity(id: 1),
            new Entity(id: 2),
            new Entity(id: 3),
            new Entity(id: 4),
            new Entity(id: 1)
    ]

    def "I can collect ids using an iterable"() {
        when:
        Set<Integer> ids = Identifiables.collect(entities)

        then:
        ids == [1, 2, 3, 4] as Set
    }

    def "I can collect ids using an iterator"() {
        when:
        Set<Integer> ids = Identifiables.collect(entities.iterator())

        then:
        ids == [1, 2, 3, 4] as Set
    }

    def "I can check if an id exists using an iterable"() {
        when:
        List<Entity> testList = entities

        then:
        Identifiables.hasId(testList, 3)
        !Identifiables.hasId(testList, 5)
    }

    def "I can check if an id exists using an iterator"() {
        when:
        List<Entity> testList = entities

        then:
        Identifiables.hasId(testList.iterator(), 3)
        !Identifiables.hasId(testList.iterator(), 5)
    }

    def "I can find by id using an iterable"() {
        when:
        Entity result = Identifiables.find(entities, 3)

        then:
        result.id == 3
        Identifiables.find(entities, 5) == null
    }

    def "I can find by id using an iteratory"() {
        when:
        Entity result = Identifiables.find(entities.iterator(), 3)

        then:
        result.id == 3
        Identifiables.find(entities.iterator(), 5) == null
    }

    def "I can collect the identifiable into a map"() {
        when:
        Map<Integer, Entity> index = Identifiables.index(entities)

        then:
        index.size() == 4
        index[1].id == 1
        index[2].id == 2
        index[3].id == 3
        index[4].id == 4
    }

    def "I can collect the iterator into a map"() {
        when:
        Map<Integer, Entity> index = Identifiables.index(entities.iterator())

        then:
        index.size() == 4
        index[1].id == 1
        index[2].id == 2
        index[3].id == 3
        index[4].id == 4
    }
}
