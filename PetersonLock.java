// Required Feature: Peterson's Lock
public class PetersonLock {

    // Per-thread "I want to enter" flags.
    private boolean flag0;
    private boolean flag1;

    // Id of whichever thread set victim last — that thread is "polite" and waits.
    private int victim;

    public void lock() {
        int i = ThreadID.get();
        int j = 1 - i;

        setFlag(i, true);
        victim = i;

        // Wait only if the other thread wants in AND we're the polite one.
        while (getFlag(j) && victim == i) {
            // busy-wait
        }
    }

    public void unlock() {
        int i = ThreadID.get();
        setFlag(i, false);
    }

    // Lets lock()/unlock() address flag0/flag1 by index (0 or 1).
    private void setFlag(int i, boolean value) {
        if (i == 0) {
            flag0 = value;
        } else {
            flag1 = value;
        }
    }

    private boolean getFlag(int i) {
        return i == 0 ? flag0 : flag1;
    }
}
