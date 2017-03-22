package main.semaphores.multiple_copies_of_resource;

import main.semaphores.PrintQueue;

/**
 * Created by G.Chalauri on 03/22/17.
 */
public class PrintJobM implements Runnable {
    private PrintQueueM printQueue;

    public PrintJobM(PrintQueueM printQueue) {
        this.printQueue = printQueue;
    }


    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n", Thread.
                currentThread().getName());

        printQueue.printJob(new Object());

        System.out.printf("%s: The document has been printed \n", Thread.currentThread().getName());
    }
}
