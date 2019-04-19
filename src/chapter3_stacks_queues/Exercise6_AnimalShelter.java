package chapter3_stacks_queues;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 18/04/19.
 */
public class Exercise6_AnimalShelter {

    private static class Animal {
        protected long timestampArrived;
    }

    private static class Cat extends Animal {
        @Override
        public String toString() {
            return "Cat";
        }
    }

    private static class Dog extends Animal {
        @Override
        public String toString() {
            return "Dog";
        }
    }

    public static class AnimalShelter {
        private Queue<Cat> catQueue;
        private Queue<Dog> dogQueue;
        private long timestamp;

        AnimalShelter() {
            catQueue = new LinkedList<>();
            dogQueue = new LinkedList<>();
        }

        // O(1) runtime
        public void enqueue(Animal animal) {
            animal.timestampArrived = timestamp;
            timestamp++;

            if (animal instanceof Cat) {
                catQueue.offer((Cat) animal);
            } else {
                dogQueue.offer((Dog) animal);
            }
        }

        // O(1) runtime
        public Cat dequeueCat() {
            if (catQueue.isEmpty()) {
                throw new RuntimeException("There are no cats");
            }
            return catQueue.poll();
        }

        // O(1) runtime
        public Dog dequeueDog() {
            if (dogQueue.isEmpty()) {
                throw new RuntimeException("There are no dogs");
            }
            return dogQueue.poll();
        }

        // O(1) runtime
        public Animal dequeueAny() {
            if (catQueue.isEmpty() && dogQueue.isEmpty()) {
                throw new RuntimeException("Animal shelter is empty");
            }
            if (catQueue.isEmpty()) return dogQueue.poll();
            if (dogQueue.isEmpty()) return catQueue.poll();

            if (catQueue.peek().timestampArrived < dogQueue.peek().timestampArrived) {
                return catQueue.poll();
            } else {
                return dogQueue.poll();
            }
        }
    }

    public static void main(String[] args) {
        AnimalShelter animalShelter = new AnimalShelter();
        animalShelter.enqueue(new Cat());
        animalShelter.enqueue(new Cat());
        animalShelter.enqueue(new Cat());

        System.out.println("Dequeue cat: " + animalShelter.dequeueCat() + " Expected: Cat");

        animalShelter.enqueue(new Dog());
        animalShelter.enqueue(new Dog());
        animalShelter.enqueue(new Cat());

        System.out.println("Dequeue cat: " + animalShelter.dequeueCat() + " Expected: Cat");
        System.out.println("Dequeue dog: " + animalShelter.dequeueDog() + " Expected: Dog");
        System.out.println("Dequeue any: " + animalShelter.dequeueAny() + " Expected: Cat");
        System.out.println("Dequeue any: " + animalShelter.dequeueAny() + " Expected: Dog");
        System.out.println("Dequeue any: " + animalShelter.dequeueAny() + " Expected: Cat");
    }

}
