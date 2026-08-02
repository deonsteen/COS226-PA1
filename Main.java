public class Main {
    private static int counter = 0;
    private static final int ITERATIONS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        TwoThreadLock lock = new LockOne();

        Thread t0 = createThread(lock, 0);
        Thread t1 = createThread(lock, 1);

        t0.setDaemon(true);
        t1.setDaemon(true);

        t0.start();
        t1.start();

        t0.join(5000);
        t1.join(5000);

        if (t0.isAlive() || t1.isAlive()) {
            System.out.println("LockOne deadlocked.");
        } else {
            System.out.println("Expected: " + (ITERATIONS * 2));
            System.out.println("Reality:   " + counter);
        }
    }

    private static Thread createThread(TwoThreadLock lock, int id) {
        return new Thread(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                lock.lock(id);

                try {
                    counter++;
                } finally {
                    lock.unlock(id);
                }
            }
        });
    }
}