package de.comparus.opensource.longmap;

/**
 * long key is the key type for the map
 * <V> is the value type for the map
 */

public class LongMapImpl<V> implements LongMap<V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75F;
    private int size;
    private float loadFactor;
    private Node[] nodes;

    /**
     * Constructs an empty LongMap with the default initial capacity(16) and load factor(0.75).
     */
    public LongMapImpl() {
        this(DEFAULT_CAPACITY, LOAD_FACTOR);
    }

    /**
     * @param capacity
     * @param loadFactor
     * Constructs an empty LongMap with the specified initial capacity and load factor.
     */
    private LongMapImpl(int capacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.nodes = new Node[capacity];
    }

    /**
     * With key value not null generate key index by method hash().
     * Key values are compared and when these parameters match
     * the element value is overwriten.
     *
     * @param key
     * @param value
     * @return V
     */
    @Override
    public V put(long key, V value) {
        int index = hash(key);

        while (nodes[index] != null) {
            V oldValue = (V) nodes[index].value;
            nodes[index].value = value;
            return oldValue;
        }

        nodes[index] = new Node(key, value);
        size += 1;

        if (size > (nodes.length * loadFactor)) {
            resize();
        }
        return null;
    }

    /**
     * Returns the value for the provided key.
     * @param key the key to get the value for
     * @return the value for the provided key
     */
    @Override
    public V get(long key) {
        Node node = nodes[hash(key)];
        if (node == null) {
            return null;
        }
        V value = null;
        for (int i = 0; i < nodes.length; i++) {
            if (node.key == -1) {
                return null;
            }
            if (node.key == key) {
                value = (V) node.value;
            }
        }
        return value;
    }

    /**
     * Removes the value with the provided key.
     * @param key the key of the value to remove
     *
     * @return the previous value for the provided key
     */
    @Override
    public V remove(long key) {

        V value;
        int byKey = searchByKey(key);
        value = (V) nodes[byKey].value;
        nodes[byKey] = null;
        size--;
        return value;
    }

    /**
     * Returns index in the Node array
     * @param key
     * @return index in the Node array
     */
    private int searchByKey(long key) {
        int index = hash(key);
        while (nodes[index] == null || nodes[index].key != key) {
            index++;
            if (index >= nodes.length) {
                index = 0;
            }
        }
        return index;
    }

    /**
     * Returns true if this map contains no key-value mappings.
     * @return true if this map contains no key-value mappings.
     *         false if this map doesn`t contain no key-value mappings.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     * @param key
     * @return true if this map contains a mapping for the specified key.
     *         false if this map doesn`t contain a mapping for the specified key.
     */
    @Override
    public boolean containsKey(long key) {

        return get(key) != null;
    }

    /**
     * Returns true if this map contains a mapping for the specified value.
     * @param value
     * @return true if this map contains a mapping for the specified value,
     *         false if this map doesn`t contain a mapping for the specified value.
     *
     */
    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null && nodes[i].value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a array of the keys contained in this map.
     * @return array of keys
     */
    @Override
    public long[] keys() {
        if (size > 0) {
            long[] keus = new long[size];
            for (int i = 0, j = 0; i < nodes.length; i++) {
                if (nodes[i] != null) {
                    keus[j] = nodes[i].key;
                    j++;
                }
            }
            return keus;
        }
        return null;
    }

    /**
     * Returns a array of the values contained in this map.
     * @return array of values
     */
    @Override
    public V[] values() {

        if (size > 0) {
            V[] values = (V[]) new Object[size];
            for (int i = 0, j = 0; i < nodes.length; i++) {
                if (nodes[i] != null) {
                    values[j] = (V) nodes[i];
                    j++;
                }
            }
            return values;
        }
        return null;
    }

    /**
     * Returns the size of the map.
     * @return the size of the map
     */
    @Override
    public long size() {
        return this.size;
    }

    /**
     * Clears the map.
     */
    @Override
    public void clear() {

        if (size > 0) {
            size = 0;
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = null;
            }
        }
    }

    /**
     * Determines the index of the value in the map
     * @param key
     * @return number of index
     */
    private int hash(long key) {
        return Math.abs((int) (key % nodes.length));
    }

    /**
     * Returns the array length
     * @return the array length
     */
    public int getNodesLength() {
        return this.nodes.length;
    }

    /**
     * When the nodes [] array is filled to the limit value,
     * its size is doubled and the elements are redistributed.
     */
    private void resize() {

        int newTableSize = 2 * nodes.length;
        Node[] oldNodes = nodes;
        nodes = new Node[newTableSize];
        size = 0;

        for (int i = 0; i < oldNodes.length; i++) {
            if (oldNodes[i] != null) {
                put(oldNodes[i].key, (V) oldNodes[i].value);
            }
        }
    }

    /**
     * item reference repository
     * @param <V> value type
     */
    static class Node<V> {

        long key;
        V value;

        private Node(long key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;

        }
    }
}

