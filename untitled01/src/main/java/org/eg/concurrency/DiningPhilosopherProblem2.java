package org.eg.concurrency;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopherProblem2 {

    private static List<Lock> chopsticks = List.of(
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    );

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(4); // Allow 4 philosophers to pick up chopsticks
        // Create an array of philosophers
        // Start each philosopher thread
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(i, semaphore)).start();
        }
    }

    static class Philosopher implements Runnable {

        private final int id;
        private final Semaphore semaphore;

        Philosopher(int id, Semaphore semaphore) {
            this.id = id;
            this.semaphore = semaphore;
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
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

            semaphore.release();
        }
    }
}