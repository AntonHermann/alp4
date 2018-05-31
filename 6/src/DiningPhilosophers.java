import java.util.concurrent.Semaphore;

import sun.text.resources.ko.FormatData_ko_KR;

public class DiningPhilosophers {
    Semaphore eats;
    Philosopher[] philosophers;
    Semaphore[] forks;

    public static void main(String[] args) {
        new DiningPhilosophers(
            "Diogenes von Sinope",
            "Aristippos von Kyrene",
            "Hildegard von Bingen",
            "Aspasia aus Milet",
            "Moderata Fonte"
        ).startDining();
    }
    public DiningPhilosophers(
        String pm1,
        String pm2,
        String pf1,
        String pf2,
        String pf3
    ) {
        eats = new Semaphore(1, true);
        philosophers = new Philosopher[5];
        forks = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
        philosophers[0] = new Philosopher(pm1, forks[0], forks[1]);
        philosophers[1] = new Philosopher(pm2, forks[1], forks[2]);
        philosophers[2] = new Philosopher(pf1, forks[2], forks[3]);
        philosophers[3] = new Philosopher(pf2, forks[3], forks[4]);
        philosophers[4] = new Philosopher(pf3, forks[4], forks[0]);
    }

    public void startDining() {
        for (Philosopher p : philosophers) {
            new Thread(p).start();
        }
        // while (true) {
        //     try {
        //         Thread.sleep(1000);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        //     System.err.println("## " + eats.availablePermits() + " " + eats.getQueueLength());
        // }
    }

    class Philosopher implements Runnable {
        String name;
        Semaphore forkLeft;
        Semaphore forkRight;

        public Philosopher(String name, Semaphore forkLeft, Semaphore forkRight) {
            this.name = name;
            this.forkLeft = forkLeft;
            this.forkRight = forkRight;
        }

        @Override
        public void run() {
            while (true) {
                think();
                eat();
            }
        }

        void think() {
            System.out.println(name + " denkt.");
            sleepRandomDuration(1000 * 10, 1000 * 20);
        }

        void eat() {
            System.out.println(name + " ist hungrig.");
            try {
                eats.acquire();
                forkLeft.acquire();
                forkRight.acquire();
                System.out.println(name + " isst.");
                sleepRandomDuration(1000 * 2, 1000 * 4);
                System.out.println(name + " hat aufgegessen.");
                // System.err.print("$$ (" +
                //     forkLeft.availablePermits() + "," +
                //     forkRight.availablePermits() + "," +
                //     eats.availablePermits() + ") - ");
                forkRight.release();
                forkLeft.release();
                eats.release();
                // System.err.println("(" +
                //     forkLeft.availablePermits() + "," +
                //     forkRight.availablePermits() + "," +
                //     eats.availablePermits() + ") - ");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    static void sleepRandomDuration(long lowerBound, long upperBound) {
        long difference = upperBound - lowerBound;
        double rnd = Math.random() * difference + lowerBound;
        try {
            // System.err.println("--" + Math.round(rnd));
            Thread.sleep(Math.round(rnd));
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}