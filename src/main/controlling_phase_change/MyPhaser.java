package main.controlling_phase_change;

import java.util.concurrent.Phaser;

/**
 * Created by G.Chalauri on 3/22/2017.
 */
public class MyPhaser extends Phaser {


    /*
    The onAdvance() method returns a Boolean value that indicates if the phaser has
    terminated or not. If the phaser returns a false value, it indicates that it hasn't terminated,
    so the threads will continue with the execution of other phases. If the phaser returns a true
    value, then the phaser still wakes up the pending threads, but moves the phaser to the
    terminated state, so all the future calls to any method of the phaser will return immediately,
    and the isTerminated() method returns the true value.
     */
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }
    }

    private boolean studentsArrived() {
        System.out.printf("Phaser: The exam are going to start. The students are ready.\n");
        System.out.printf("Phaser: We have %d students.\n", getRegisteredParties());
        return false;
    }

    private boolean finishFirstExercise() {
        System.out.printf("Phaser: All the students have finished the first exercise.\n");
        System.out.printf("Phaser: It's time for the second one.\n");
        return false;
    }

    private boolean finishSecondExercise() {
        System.out.printf("Phaser: All the students have finished the second exercise.\n");
        System.out.printf("Phaser: It's time for the third one.\n");
        return false;
    }

    private boolean finishExam() {
        System.out.printf("Phaser: All the students have finished the exam.\n");
        System.out.printf("Phaser: Thank you for your time.\n");
        return true;
    }
}
