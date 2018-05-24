import java.io.Console;
import java.util.concurrent.Semaphore;

public class DogSchool {
    static final int W_PER_A = 10;
    static final long A_DELAY = 10 * 1000;
    static final long W_DELAY = 10 * 1000;

    Semaphore freie_plaetze;
    int a_counter = 0;
    int w_counter = 0;

    public DogSchool() {
        freie_plaetze = new Semaphore(0);
    }

    public void neueAufsichtsperson() {
        freie_plaetze.release(W_PER_A);
        a_counter += 1;
        new Thread(new Aufsichtsperson(), "A" + a_counter).start();
    }

    public boolean neuerWelpe() {
        // freie_plaetze.acquire();
        boolean res = freie_plaetze.tryAcquire();
        if (res) {
            w_counter += 1;
            new Thread(new Welpe(), "W" + w_counter).start();
        }
        return res;
    }

    public static void main(String[] args) {
        DogSchool ds = new DogSchool();

        Console console = System.console();
        System.out.println("Sie haben die Wahl zwischen folgenden Aktionen:");
        System.out.println("A: Neue Aufsichtsperson");
        System.out.println("W: Neuer Welpe");
        System.out.println("Q: Beenden");
        String input;
        while (true) {
            input = console.readLine().trim().toLowerCase();
            if (input.charAt(0) == 'a') {
                ds.neueAufsichtsperson();
            } else if (input.charAt(0) == 'w') {
                if (!ds.neuerWelpe()) {
                    System.out.println("Es ist kein Platz! -_-");
                }
            } else if (input.charAt(0) == 'w') {
                break;
            }
        }
    }

    class Aufsichtsperson implements Runnable {
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + "beginnt zu arbeiten");
            try {
                Thread.sleep(A_DELAY);
                System.out.println(name + "möchte nach Hause gehen");
                freie_plaetze.acquire(W_PER_A);
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println(name + "geht nach Hause");
        }
    }

    class Welpe implements Runnable {
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + "beginnt zu lernen");
            try {
                Thread.sleep(W_DELAY);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println(name + "geht nach Hause");
            freie_plaetze.release();
        }
    }
}