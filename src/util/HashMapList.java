package util;

import java.util.*;

/**
 * Created by Rene Argento on 30/03/20.
 */
public class HashMapList<T, E> {
    private Map<T, List<E>> map = new HashMap<>();

    // O(1) runtime
    public void put(T key, E item) {
        List<E> items = map.getOrDefault(key, new ArrayList<>());
        items.add(item);
        map.put(key, items);
    }

    // O(1) runtime
    public void put(T key, ArrayList<E> items) {
        map.put(key, items);
    }

    // O(1) runtime
    public List<E> get(T key) {
        return map.get(key);
    }

    // O(1) runtime
    public boolean containsKey(T key) {
        return map.containsKey(key);
    }

    // O(v) runtime, where v is the number of values in the map
    public boolean containsKeyValue(T key, E value) {
        List<E> list = get(key);
        if (list == null) {
            return false;
        }
        return list.contains(value);
    }

    // O(1) runtime
    public Set<T> keySet() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
