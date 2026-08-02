 
import java.util.concurrent.atomic.AtomicBoolean;
 
public class LockOne implements TwoThreadLock {
 
    private final AtomicBoolean[] flag = { new AtomicBoolean(false), new AtomicBoolean(false) };
 
    @Override
    public void lock(int threadId) {
        validateThreadId(threadId);
 
        int otherThreadId = 1 - threadId;
        flag[threadId].set(true);
 
        while (flag[otherThreadId].get()) {
            //busy wait
        }
    }
 
    @Override
    public void unlock(int threadId) {
        validateThreadId(threadId);
        flag[threadId].set(false);
    }
 
    private void validateThreadId(int threadId) {
        if (threadId != 0 && threadId != 1) {
            throw new IllegalArgumentException(
                "Thread ID must be 0 or 1."
            );
        }
    }
}
