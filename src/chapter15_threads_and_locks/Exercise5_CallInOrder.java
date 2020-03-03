package chapter15_threads_and_locks;

import java.util.concurrent.Semaphore;

/**
 * Created by Rene Argento on 02/03/20.
 */
public class Exercise5_CallInOrder {

    public class Foo {
        private Semaphore semaphore1;
        private Semaphore semaphore2;

        public Foo() {
            semaphore1 = new Semaphore(1);
            semaphore2 = new Semaphore(1);

            try {
                semaphore1.acquire();
                semaphore2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void first() {
            System.out.println("Starting first() execution");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finishing first() execution");
            semaphore1.release();
        }

        public void second() {
            try {
                semaphore1.acquire();
                semaphore1.release();

                System.out.println("Starting second() execution");
                Thread.sleep(2000);
                System.out.println("Finishing second() execution");
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void third() {
            try {
                semaphore2.acquire();
                semaphore2.release();

                System.out.println("Starting third() execution");
                Thread.sleep(2000);
                System.out.println("Finishing third() execution");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Foo foo = new Exercise5_CallInOrder().new Foo();

        Runnable threadA = foo::first;
        threadA.run();

        Runnable threadB = foo::second;
        threadB.run();

        Runnable threadC = foo::third;
        threadC.run();
    }

}
