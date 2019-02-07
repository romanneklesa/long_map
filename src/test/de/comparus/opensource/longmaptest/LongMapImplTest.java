package de.comparus.opensource.longmaptest;

import de.comparus.opensource.longmap.LongMapImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LongMapImplTest {

    private LongMapImpl map;

    @Before
    public void setUp() {

        map = new LongMapImpl();
    }

    public LongMapImpl<String> putValues(int size) {

        LongMapImpl<String> longMap = new LongMapImpl<>();
        for (int i = 0; i < size; i++) {
            longMap.put(i, "value");
        }
        return longMap;
    }

    @Test
    public void testPut() {

        map.put(-648568, "a");
        map.put(654, "b");
        assertNotNull("put a new value", map.put(654, "b"));
        map.put(654, "c");

        map.put(Long.MAX_VALUE, "d");
        map.put(Long.MIN_VALUE, "e");

        assertEquals("a", map.put(-648568, "a"));
        assertEquals("c", map.put(654, "c"));
        assertEquals("d", map.put(Long.MAX_VALUE, "d"));
        assertEquals("e", map.put(Long.MIN_VALUE, "e"));
    }

    @Test
    public void testGet() {

        map = putValues(5);
        assertNotNull(map.get(2));
        assertEquals("value", map.get(2));
    }

    @Test
    public void testRemove() {

        int expectedSize = 20;
        map = putValues(expectedSize);
        assertEquals("Size before removing value", expectedSize, map.size());

        int key = 14;
        map.remove(key);
        assertEquals("size after removing value", expectedSize - 1, map.size());
        assertTrue("key after removing value", map.get(key) == null);
    }

    @Test
    public void testIsEmptyForNewMap() {
        assertTrue("Check that a LongMap returns 'true' for isEmpty()", map.isEmpty());
    }

    @Test
    public void testIsEmptyAfterAddingAnElement() {

        map = putValues(1);
        assertFalse("After adding values makes isEmpty() return 'false'", map.isEmpty());
    }

    @Test
    public void testSize() {

        assertEquals("Check that size returns 0 for the new LongMap", 0, map.size());

        map = putValues(5);
        assertEquals("Check that size returns size the LongMap after adding values", 5, map.size());

    }

    @Test
    public void testResize() {

        assertEquals("Check that size of " +
                "object \"map\" equals [16] after innitialization a new empty object", 16, map.getNodesLength());

        map = putValues(1);
        assertEquals("Chaeck after adding value", 16, map.getNodesLength());

        map = putValues(17);
        assertEquals("Checking capacity increase after adding more than 17 values", 32, map.getNodesLength());
    }

    @Test
    public void testGetKeys() {
        assertArrayEquals("Check empty varaible",null, map.keys());

        map.put(1, "a");
        map.put(-5, "b");
        map.put(300, "c");
        long[] array = {1, -5, 300};
        assertArrayEquals("Check after adding values", array, map.keys());
    }

    @Test
    public void testGetValues() {
        map = new LongMapImpl<>();
        assertArrayEquals("Check empty varaible",null, map.values());

        map.put(1, "a");
        map.put(-5, "b");
        map.put(300, "c");
        String[] array = {"value=a", "value=b", "value=c"};
        assertEquals("Check after adding values" ,Arrays.toString(array), Arrays.toString(map.values()));
    }

    @Test
    public void testContainsKey() {

        map = putValues(2);
        assertTrue("If key contains: ", map.containsKey(1));
    }

    @Test
    public void testContainsValues() {

        map = putValues(2);
        assertTrue("If value contains: ", map.containsValue("value"));
    }
}
