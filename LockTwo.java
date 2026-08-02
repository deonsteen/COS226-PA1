public class LockTwo implements TwoThreadLock {

    private volatile int victim;

    @Override
    public void lock(int threadId) {
        victim = threadId;

        while (victim == threadId) {
            // busy-wait
        }
    }

    @Override
    public void unlock(int threadId) {
        // no-op: LockTwo requires no release action
    }
}
