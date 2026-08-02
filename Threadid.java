import java.util.concurrent.atomic.AtomicInteger;

public class Threadid
{
    private static final AtomicInteger nextId = new AtomicInteger(0);
    private static final ThreadLocal<Integer> threadID = ThreadLocal.withInitial(() -> nextId.getAndIncrement());
    public static int get() 
    {
        return threadID.get();
    }
}