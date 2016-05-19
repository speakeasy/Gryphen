package org.speakeasy.gryphen;

/*
 * 
 *
 */
public class Gryphen {

    public static void main(String[] args) {
        try {
            // Start console thread.
            (new Console()).start();
            // Just wait for interrupt.
            while (true) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException ex) {
            System.exit(1);
        }
    }

}
