/*Common interface*/
public interface TwoThreadLock {
    void lock(int threadId);
    void unlock(int threadId);
}
