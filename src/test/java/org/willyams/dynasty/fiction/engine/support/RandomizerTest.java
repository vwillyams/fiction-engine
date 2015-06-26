package org.willyams.dynasty.fiction.engine.support;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RandomizerTest {

    private static final Randomizer randomizer = Randomizer.instance();
    private static final int RUN_TIMES = 1000;

    public void testRandomMinMax() {
        Set<Integer> results = new HashSet<Integer>();
        int min = 1;
        int max = 20;
        for (int i = 0; i < RUN_TIMES; i++) {
            results.add(randomizer.getInt(min, max));
        }
        for (int i = min; i < max; i++) {
            assertTrue("Result contained " + i + " with expected min of " + min + "and max of " + max, results.contains(i));
        }
        assertFalse(results.contains(min - 1));
        assertFalse(results.contains(max + 1));
    }

    public void testArrayRandomization() {
        Set<Integer> results = new HashSet<Integer>();
        int max = 20;
        for (int i = 0; i < RUN_TIMES; i++) {
            results.add(randomizer.getIntForArray(max));
        }
        for (int i = 0; i < max - 1; i++) {
            assertTrue("Result contained " + i + " with expected min of 0 and max of " + max, results.contains(i));
        }
        assertFalse(results.contains(-1));
        assertFalse(results.contains(max));
    }

    public void testMinMaxNegatives() {
        Set<Integer> results = new HashSet<Integer>();
        int min = -20;
        int max = -5;
        for (int i = 0; i < RUN_TIMES; i++) {
            results.add(randomizer.getInt(min, max));
        }
        for (int i = min; i < max; i++) {
            assertTrue("Result contained " + i + " with expected min of " + min + "and max of " + max, results.contains(i));
        }
        assertFalse(results.contains(min - 1));
        assertFalse(results.contains(max + 1));
    }

    public void testDiceRoll() {
        Set<Integer> results = new HashSet<Integer>();
        int min = 3;
        int max = 18;
        for (int i = 0; i < RUN_TIMES; i++) {
            results.add(randomizer.dice(3, 6));
        }
        for (int i = min; i < max - 1; i++) {
            assertTrue("Result contained " + i + " with expected min of " + min + "and max of " + max, results.contains(i));
        }
        assertFalse(results.contains(min - 1));
        assertFalse(results.contains(max + 1));
    }
}
