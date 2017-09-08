package com.vzaar;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for working with entities with ids
 */
public final class Identifiables {

    private Identifiables() {

    }

    /**
     * Collect all the unique ids from the input.
     * @param iterable an iterable of Identifiable objects
     * @param <T> the entity class the implements Identifiable
     * @return a set of ids found by iterating over the input
     */
    public static <T extends Identifiable> Set<Integer> collect(Iterable<T> iterable) {
        return collect(iterable.iterator());
    }

    /**
     * Collect all the unique ids from the input.
     * @param iterator an iterator of Identifiable objects
     * @param <T> the entity class the implements Identifiable
     * @return a set of ids found by iterating over the input
     */
    public static <T extends Identifiable> Set<Integer> collect(Iterator<T> iterator) {
        Set<Integer> ids = new LinkedHashSet<>();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getId());
        }
        return ids;
    }

    /**
     * Discover if the input contains an entity with the requested id.
     * @param iterable an iterable of Identifiable objects
     * @param id the id to query for
     * @param <T> the entity class the implements Identifiable
     * @return true if an item was found with the queried parameter
     */
    public static <T extends Identifiable> boolean hasId(Iterable<T> iterable, int id) {
        return hasId(iterable.iterator(), id);
    }

    /**
     * Discover if the input contains an entity with the requested id.
     * @param iterator an iterator of Identifiable objects
     * @param id the id to query for
     * @param <T> the entity class the implements Identifiable
     * @return true if an item was found with the queried parameter
     */
    public static <T extends Identifiable> boolean hasId(Iterator<T> iterator, int id) {
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find the entity with the given id
     * @param iterable an iterable of Identifiable objects
     * @param id the id to query for
     * @param <T> the entity class the implements Identifiable
     * @return the entity with the id or null if none found
     */
    public static <T extends Identifiable> T find(Iterable<T> iterable, int id) {
        return find(iterable.iterator(), id);
    }

    /**
     * Find the entity with the given id
     * @param iterator an iterator of Identifiable objects
     * @param id the id to query for
     * @param <T> the entity class the implements Identifiable
     * @return the entity with the id or null if none found
     */
    public static <T extends Identifiable> T find(Iterator<T> iterator, int id) {
        while (iterator.hasNext()) {
            T identifiable = iterator.next();
            if (identifiable.getId() == id) {
                return identifiable;
            }
        }
        return null;
    }

    /**
     * Create a map of the idenfifiable object with the id as the key
     * @param iterable an iterable of Identifiable objects
     * @param <T> the entity class the implements Identifiable
     * @return a map of ids to idenfiable objects
     */
    public static <T extends Identifiable> Map<Integer, T> index(Iterable<T> iterable) {
        return index(iterable.iterator());
    }

    /**
     * Create a map of the idenfifiable object with the id as the key
     * @param iterator an iterator of Identifiable objects
     * @param <T> the entity class the implements Identifiable
     * @return a map of ids to idenfiable objects
     */
    public static <T extends Identifiable> Map<Integer, T> index(Iterator<T> iterator) {
        Map<Integer, T> index = new HashMap<Integer, T>();
        while (iterator.hasNext()) {
            T identifiable = iterator.next();
            index.put(identifiable.getId(), identifiable);
        }
        return index;
    }
}
