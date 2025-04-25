package org.eg.concurrency;

import java.util.List;
import java.util.concurrent.locks.Lock;

public class DiningPhilosopherProblem {

    private static List<Lock> chopsticks = List.of(
            new java.util.concurrent.locks.ReentrantLock(),
            new java.util.concurrent.locks.ReentrantLock(),
            new java.util.concurrent.locks.ReentrantLock(),
            new java.util.concurrent.locks.ReentrantLock(),
            new java.util.concurrent.locks.ReentrantLock()
    );

    public static void main(String[] args) {
        // Create an array of philosophers
        Philosopher[] philosophers = new Philosopher[5];
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i);
        }

        // Start each philosopher thread
        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher).start();
        }
    }

    static class Philosopher implements Runnable {

        private final int id;

        Philosopher(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                think();
                pickUpChopsticks();
                eat();
                putDownChopsticks();
            }
        }

        private void think() {
            System.out.printf("Philosopher %d is thinking...\n", id);
        }

        private void pickUpChopsticks() {
            chopsticks.get(id).lock();
            System.out.printf("Philosopher %d is picking up right chopstick...\n", id);
            chopsticks.get((id + 1) % chopsticks.size()).lock();
            System.out.printf("Philosopher %d is picking up left chopstick...\n", id);
        }

        private void eat() {
            System.out.printf("Philosopher %d is eating...\n", id);
        }

        private void putDownChopsticks() {
            chopsticks.get((id + 1) % chopsticks.size()).unlock();
            System.out.printf("Philosopher %d is putting down left chopstick...\n", id);
            chopsticks.get(id).unlock();
            System.out.printf("Philosopher %d is putting down right chopstick...\n", id);
        }
    }
}
