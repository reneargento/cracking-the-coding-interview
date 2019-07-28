package chapter6_math_logic_puzzles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/07/19.
 */
public class Exercise10_Poison {

    private static class Bottle {
        private int id;
        private boolean isPoisoned;

        Bottle(int id) {
            this.id = id;
        }

        public void addPoison() {
            this.isPoisoned = true;
        }
    }

    private static class TestStrip {
        private int id;
        private boolean isPositive;

        TestStrip(int id) {
            this.id = id;
        }

        public void applyDrop(Bottle bottle) {
            if (bottle.isPoisoned) {
                isPositive = true;
            }
        }

        public boolean isPositive() {
            return isPositive;
        }
    }

    public static void main(String[] args) {
        int poisonedBottleId = 487;
        List<Bottle> bottles = generateBottles(poisonedBottleId);
        List<TestStrip> testStrips = generateTestStrips();
        applyDrops(bottles, testStrips);
        int poisonedBottleIdDiscovered = getPoisonedBottleId(testStrips);

        if (poisonedBottleId == poisonedBottleIdDiscovered) {
            System.out.println("Found the correct bottle!");
        } else {
            System.out.println("Something went wrong.");
        }
    }

    private static List<Bottle> generateBottles(int poisonedBottleId) {
        List<Bottle> bottles = new ArrayList<>();

        for (int id = 0; id < 1000; id++) {
            Bottle bottle = new Bottle(id);
            bottles.add(bottle);

            if (id == poisonedBottleId) {
                bottle.addPoison();
            }
        }

        return bottles;
    }

    private static List<TestStrip> generateTestStrips() {
        List<TestStrip> testStrips = new ArrayList<>();

        for (int id = 0; id < 10; id++) {
            testStrips.add(new TestStrip(id));
        }

        return testStrips;
    }

    private static void applyDrops(List<Bottle> bottles, List<TestStrip> testStrips) {
        for (Bottle bottle : bottles) {
            int id = bottle.id;
            int stripId = 0;

            while (id > 0) {
                if ((id & 1) == 1) {
                    testStrips.get(stripId).applyDrop(bottle);
                }

                id >>= 1;
                stripId++;
            }
        }
    }

    private static int getPoisonedBottleId(List<TestStrip> testStrips) {
        int poisonedBottleId = 0;

        for (int id = 0; id < testStrips.size(); id++) {
            if (testStrips.get(id).isPositive()) {
                poisonedBottleId |= 1 << id;
            }
        }
        return poisonedBottleId;
    }
}
