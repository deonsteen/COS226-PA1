import java.util.concurrent.atomic.AtomicInteger;

// Assigns each calling thread a stable id: 0 for the first, 1 for the second.
public class ThreadID {

    private static final AtomicInteger nextId = new AtomicInteger(0);
    private static final ThreadLocal<Integer> id =
            ThreadLocal.withInitial(nextId::getAndIncrement);

    public static int get() {
        return id.get();
    }
}
