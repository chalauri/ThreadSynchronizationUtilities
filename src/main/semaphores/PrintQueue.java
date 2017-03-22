package main.semaphores;

import java.util.concurrent.Semaphore;

/**
 * Created by G.Chalauri on 03/22/17.
 */

/*
important point in this example is the constructor of the PrintQueue class and
the initialization of the Semaphore object. You pass the value 1 as the parameter of this
constructor, so you are creating a binary semaphore. The initial value of the internal counter
is 1, so you will protect the access to one shared resource, in this case, the print queue.
 */
public class PrintQueue {
    private final Semaphore semaphore;

    public PrintQueue() {
        semaphore = new Semaphore(1);
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n", Thread.currentThread().getName(), duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

}
