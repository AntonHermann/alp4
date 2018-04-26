public class ExerciceRunnable implements Runnable {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Missing argument: n");
            System.exit(1);
        }
        int n = Integer.parseInt(args[0]);

        for (int i = 0; i < n; i++) {
            (new Thread(new ExerciceRunnable())).start();
        }
    }

    @Override public void run() {
        int m = (int)(Math.random() * 5 + 1);
        String name = Thread.currentThread().getName();

        try {
            for (int i = 0; i < m; i++) {
                System.out.format("%d/%d in %s%n", i + 1, m, name);
                Thread.sleep(1000);
            }
        } catch (InterruptedException iex) {}
    }
}