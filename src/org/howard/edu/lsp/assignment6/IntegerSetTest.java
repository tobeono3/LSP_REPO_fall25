package org.howard.edu.lsp.assignment6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegerSetTest {

    @Test
    public void testAddAndContains() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        assertTrue(set.contains(1));
        set.add(1);  // duplicate ignored
        assertEquals(1, set.length());
    }

    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        assertFalse(set.isEmpty());
        set.clear();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testLength() {
        org.howard.edu.lsp.assignment6.IntegerSet set = new IntegerSet();
        assertEquals(0, set.length());
        set.add(10);
        set.add(20);
        assertEquals(2, set.length());
    }

    @Test
    public void testLargest() {
        IntegerSet set = new IntegerSet();
        set.add(3);
        set.add(10);
        set.add(7);
        assertEquals(10, set.largest());
    }

    @Test
    public void testLargestThrows() {
        org.howard.edu.lsp.assignment6.IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.largest());
    }

    @Test
    public void testSmallest() {
        IntegerSet set = new org.howard.edu.lsp.assignment6.IntegerSet();
        set.add(9);
        set.add(2);
        set.add(5);
        assertEquals(2, set.smallest());
    }

    @Test
    public void testSmallestThrows() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, () -> set.smallest());
    }

    @Test
    public void testEqualsOrderIndependent() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        a.add(2);

        b.add(2);
        b.add(1);

        assertTrue(a.equals(b));
    }

    @Test
    public void testNotEqualsDifferentSizes() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        b.add(1);
        b.add(2);

        assertFalse(a.equals(b));
    }

    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.remove(1);
        assertFalse(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    public void testUnion() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        a.add(2);

        b.add(2);
        b.add(3);

        a.union(b);

        assertEquals(3, a.length());
        assertTrue(a.contains(1));
        assertTrue(a.contains(2));
        assertTrue(a.contains(3));
    }

    @Test
    public void testIntersect() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        a.add(2);
        a.add(3);

        b.add(2);
        b.add(4);

        a.intersect(b);

        assertEquals(1, a.length());
        assertTrue(a.contains(2));
        assertFalse(a.contains(1));
        assertFalse(a.contains(3));
    }

    @Test
    public void testDiff() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        a.add(2);
        a.add(3);

        b.add(2);

        a.diff(b);

        assertFalse(a.contains(2));
        assertTrue(a.contains(1));
        assertTrue(a.contains(3));
    }

    @Test
    public void testComplement() {
        IntegerSet a = new IntegerSet();
        IntegerSet b = new IntegerSet();

        a.add(1);
        a.add(3);

        b.add(1);
        b.add(2);
        b.add(3);
        b.add(4);

        a.complement(b);

        assertEquals(2, a.length());
        assertTrue(a.contains(2));
        assertTrue(a.contains(4));
    }

    @Test
    public void testToStringFormat() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        String s = set.toString();

        assertTrue(s.startsWith("["));
        assertTrue(s.endsWith("]"));
        assertTrue(s.contains("1"));
        assertTrue(s.contains("2"));
    }
}
