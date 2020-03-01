package chapter15_threads_and_locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Rene Argento on 01/03/20.
 */
public class Exercise3_DiningPhilosophers {

    public class Chopstick {
        private Lock lock;
        private int number;

        public Chopstick(int number) {
            lock = new ReentrantLock();
            this.number = number;
        }

        public void pickUp() {
            lock.lock();
        }

        public void putDown() {
            lock.unlock();
        }

        public int getNumber() {
            return number;
        }
    }

    public class Philosopher extends Thread {
        private int bites = 10;
        private Chopstick lower, higher;
        private int index;

        public Philosopher(int index, Chopstick left, Chopstick right) {
            this.index = index;

            if (left.getNumber() < right.getNumber()) {
                lower = left;
                higher = right;
            } else {
                lower = right;
                higher = left;
            }
        }

        public void pickUp() {
            lower.pickUp();
            higher.pickUp();
        }

        public void chew() { }

        public void putDown() {
            higher.putDown();
            lower.putDown();
        }

        public void eat() {
            pickUp();
            chew();
            putDown();
        }

        public void run() {
            for (int i = 0; i < bites; i++) {
                eat();
            }
        }
    }
}
