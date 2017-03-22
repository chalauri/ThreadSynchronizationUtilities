package main;

import main.controlling_phase_change.MyPhaser;
import main.controlling_phase_change.Student;
import main.phaser.FileSearch;
import main.semaphores.Job;
import main.semaphores.PrintQueue;
import main.semaphores.multiple_copies_of_resource.PrintJobM;
import main.semaphores.multiple_copies_of_resource.PrintQueueM;
import main.synchronizing_tasks_in_common_point.Grouper;
import main.synchronizing_tasks_in_common_point.MatrixMock;
import main.synchronizing_tasks_in_common_point.Results;
import main.synchronizing_tasks_in_common_point.Searcher;
import main.waiting_for_multiple_concurent_events.Participant;
import main.waiting_for_multiple_concurent_events.VideoConference;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * Created by G.Chalauri on 03/22/17.
 */
public class Main {

    public static void main(String[] args) {

        // semaphoreExample();
        // multipleCopiesOfResourcesExample();
        // videoConferenceExample();
        //cyclicBarrierExample();
        //phaserExample();
        customizingPhaserExample();
    }

    private static void customizingPhaserExample() {
        MyPhaser phaser = new MyPhaser();

        Student students[] = new Student[5];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread threads[] = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i], "Student " + i);
            threads[i].start();
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Main: The phaser has finished: %s.\n", phaser.isTerminated());
    }

    private static void phaserExample() {
        Phaser phaser = new Phaser(3);
        FileSearch system = new FileSearch("C:\\Windows", "java",
                phaser);
        FileSearch apps =
                new FileSearch("E:\\", "java", phaser);
        FileSearch documents =
                new FileSearch("C:\\Documents And Settings", "java", phaser);

        Thread systemThread = new Thread(system, "System");
        systemThread.start();

        Thread appsThread = new Thread(apps, "Apps");
        appsThread.start();

        Thread documentsThread = new Thread(documents, "Documents");
        documentsThread.start();

        try {
            systemThread.join();
            appsThread.join();
            documentsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Terminated: " + phaser.isTerminated());
    }

    private static void cyclicBarrierExample() {
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANT = 2000;

        MatrixMock mock = new MatrixMock(ROWS, NUMBERS, SEARCH);

        Results results = new Results(ROWS);

        Grouper grouper = new Grouper(results);

        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

        Searcher searchers[] = new Searcher[PARTICIPANTS];
        for (int i = 0; i < PARTICIPANTS; i++) {
            searchers[i] = new Searcher(i * LINES_PARTICIPANT, (i * LINES_PARTICIPANT) + LINES_PARTICIPANT, mock, results, 5, barrier);
            Thread thread = new Thread(searchers[i]);
            thread.start();
        }
        System.out.printf("Main: The main thread has finished.\n");
    }

    private static void videoConferenceExample() {
        VideoConference conference = new VideoConference(10);

        Thread threadConference = new Thread(conference);
        threadConference.start();

        for (int i = 0; i < 10; i++) {
            Participant p = new Participant(conference, "Participant " + i);
            Thread t = new Thread(p);
            t.start();
        }
    }

    private static void semaphoreExample() {
        PrintQueue printQueue = new PrintQueue();

        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new Job(printQueue), "Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }

    /*
    The key of this example is in the PrintQueue class. The Semaphore object is created
    using 3 as the parameter of the constructor. The first three threads that call the acquire()
    method will get the access to the critical section of this example, while the rest will be
    blocked. When a thread finishes the critical section and releases the semaphore, another
    thread will acquire it.
    In this critical section, the thread gets the index of the printer assigned to print this job. This
    part of the example is used to give more realism to the example, but it doesn't use any code
    related with semaphores.
     */
    private static void multipleCopiesOfResourcesExample() {
        PrintQueueM printQueue = new PrintQueueM();

        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new PrintJobM(printQueue), "Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            thread[i].start();
        }
    }
}
