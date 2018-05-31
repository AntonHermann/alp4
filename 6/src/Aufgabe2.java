import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.LinkedList;


public class Aufgabe2 {
    Producer Pro;
    Consumer Cs;

    public Aufgabe2(int N) {
        Buffer bf = new Buffer(N);
        Pro = new Producer(bf);
        Cs = new Consumer(bf);
    }

    public static void main(String[] args) {
        Aufgabe2 ab = new Aufgabe2(10);
        ab.Pro.start();
        ab.Cs.start();
    }

    class Buffer {
        int[] buff;
        int length;
        Semaphore notFull;
        Semaphore notEmpty;

        public Buffer(int length) {
            this.length = length;
            buff = new int[length];

            notFull = new Semaphore(0);
            notEmpty = new Semaphore(0);

            notEmpty.drainPermits(); // Starting configuration
            notFull.release(10);
        }

    }

    class Producer extends Thread {
        int head;
        Buffer Buff;
        Random rand;

        public Producer(Buffer Buff) {
            this.Buff = Buff;
            head = 0;
            rand = new Random();
        }

        public void run() {
            System.out.println("Producer Thread started. Thread-Name: " + this.getName());

            try {
                while (true) {
                    Buff.notFull.acquire();
                    Buff.buff[head] = rand.nextInt(10);
                    System.out.println(this.getName() + ": Writing " + Buff.buff[head]);
                    head = (head + 1) % Buff.length;
                    Buff.notEmpty.release();
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    class Consumer extends Thread {
        int tail;
        Buffer Buff;
        LinkedList read;

        public Consumer(Buffer Buff) {
            this.Buff = Buff;
            tail = 0;
            read = new LinkedList();
        }

        public void run() {
            System.out.println("Consumer Thread started. Thread-Name: " + this.getName());
            try {
                while (true) {
                    Buff.notEmpty.acquire();
                    System.out.println(this.getName() + ": Reading " + Buff.buff[tail]);
                    read.add(Buff.buff[tail]);
                    tail = (tail + 1) % Buff.length;
                    Buff.notFull.release();
                }
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

