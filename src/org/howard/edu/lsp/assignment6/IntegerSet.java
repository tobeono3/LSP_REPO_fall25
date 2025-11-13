package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * IntegerSet models a mathematical set of integers.
 * It stores unique integers and supports standard set operations.
 */
public class IntegerSet  {
    private List<Integer> set = new ArrayList<>();

    /**
     * Clears all elements from the set.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     * @return number of items
     */
    public int length() {
        return set.size();
    }

    /**
     * Checks if two sets contain exactly the same elements (order doesn't matter).
     * @param o object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerSet)) return false;

        IntegerSet other = (IntegerSet) o;

        if (this.length() != other.length()) return false;

        List<Integer> a = new ArrayList<>(this.set);
        List<Integer> b = new ArrayList<>(other.set);

        Collections.sort(a);
        Collections.sort(b);

        return a.equals(b);
    }

    /**
     * Returns true if the set contains the value.
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest integer in the set.
     * @throws IllegalStateException if empty
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return Collections.max(set);
    }

    /**
     * Returns the smallest integer in the set.
     * @throws IllegalStateException if empty
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return Collections.min(set);
    }

    /**
     * Adds an element to the set if not already present.
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an element from the set if it exists.
     */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /**
     * Modifies this set to contain the union of itself and another set.
     */
    public void union(IntegerSet other) {
        for (int x : other.set) {
            if (!this.set.contains(x)) {
                this.set.add(x);
            }
        }
    }

    /**
     * Modifies this set to contain only the intersection with another set.
     */
    public void intersect(IntegerSet other) {
        this.set.retainAll(other.set);
    }

    /**
     * Modifies this set to remove all elements found in the other set.
     */
    public void diff(IntegerSet other) {
        this.set.removeAll(other.set);
    }

    /**
     * Modifies this to become (other \ this).
     * All elements in "other" that are NOT in "this".
     */
    public void complement(IntegerSet other) {
        List<Integer> result = new ArrayList<>();
        for (int x : other.set) {
            if (!this.set.contains(x)) {
                result.add(x);
            }
        }
        this.set = result;
    }

    /**
     * Returns true if the set has no elements.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Formats the set as a string like [1, 2, 3].
     */
    @Override
    public String toString() {
        return set.toString();
    }
}
