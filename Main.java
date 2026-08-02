public class Main {
    private static int counter = 0;
    private static final int ITERATIONS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        runDemo("LockOne", new LockOne());
        runDemo("LockTwo", new LockTwo());
        runDemo("PetersonLock", new PetersonLock());
    }

    private static void runDemo(String name, TwoThreadLock lock) throws InterruptedException {
        counter = 0;

        System.out.println("=== " + name + " ===");

        Thread t0 = createThread(lock, 0);
        Thread t1 = createThread(lock, 1);

        t0.setDaemon(true);
        t1.setDaemon(true);

        t0.start();
        t1.start();

        t0.join(1500);
        t1.join(1500);

        if (t0.isAlive() || t1.isAlive()) {
            System.out.println(name + " deadlocked.");
        } else {
            System.out.println("Expected: " + (ITERATIONS * 2));
            System.out.println("Reality:   " + counter);
        }

        System.out.println();
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
