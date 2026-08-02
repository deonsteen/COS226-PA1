import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicBoolean;



    public class LockTwoDemo {
 
    private static final LockTwo lock = new LockTwo();
 
    private static final AtomicInteger insideCS = new AtomicInteger(0);   // threads currently in the critical section
    private static final AtomicLong totalEntries = new AtomicLong(0);    // how many times the critical section was entered
    private static final AtomicBoolean violationDetected = new AtomicBoolean(false);
    private static final AtomicBoolean stopRequested = new AtomicBoolean(false);
 
    public static void main(String[] args) throws InterruptedException {
 
        Runnable task = () -> {
            while (!stopRequested.get()) {
                lock.lock();
 
                int nowInside = insideCS.incrementAndGet();
                if (nowInside != 1) {
                    // more than one thread inside at once -- mutual exclusion broken
                    violationDetected.set(true);
                }
                totalEntries.incrementAndGet();
 
                insideCS.decrementAndGet();
 
                lock.unlock();
            }
        };
 
        Thread t0 = new Thread(task, "Thread-0");
        Thread t1 = new Thread(task, "Thread-1");
 
        // Daemon threads: if either is ever stuck spinning after
        // stopRequested is set (see class comment above), it must not
        // prevent the JVM from exiting.
        t0.setDaemon(true);
        t1.setDaemon(true);
 
        t0.start();
        t1.start();
 
        Thread.sleep(2000);
        stopRequested.set(true);
 
        // Give any in-flight critical section entries a brief moment to settle.
        Thread.sleep(50);
 
        System.out.println("Critical section entries observed: " + totalEntries.get());
 
        if (violationDetected.get()) {
            System.out.println("FAILURE: mutual exclusion violated -- "
                    + "more than one thread was inside the critical section at once.");
        } else {
            System.out.println("SUCCESS: LockTwo correctly enforced mutual exclusion -- "
                    + "no thread ever entered the critical section while another was inside.");
        }
 
        // Force exit even if a daemon thread is still spinning.
        System.exit(violationDetected.get() ? 1 : 0);
    }
}

