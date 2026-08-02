# COS 226 – Practical 1: Mutual Exclusion Algorithms

## Overview

This project implements and demonstrates three classical software mutual
exclusion algorithms using two concurrent threads:

1. **LockOne**
2. **LockTwo**
3. **Peterson's Lock**

Each algorithm is run against the same test harness (`Main.java`), which
starts two threads that repeatedly acquire the lock, increment a shared
counter inside the critical section, and release the lock.

## Files

| File                 | Purpose                                              |
|----------------------|-------------------------------------------------------|
| `TwoThreadLock.java` | Common interface shared by all three lock algorithms (`lock(int threadId)` / `unlock(int threadId)`). |
| `LockOne.java`       | Implementation of the LockOne algorithm.              |
| `LockTwo.java`       | Implementation of the LockTwo algorithm.              |
| `PetersonLock.java`  | Implementation of Peterson's Lock.                    |
| `Threadid.java`      | Helper class that assigns a unique thread ID (0 or 1) via `ThreadLocal`. |
| `Main.java`          | Test harness that runs all three algorithms with two threads and reports results. |
| `Lock.java`          | (Unused / legacy no-arg lock interface — not required by the current implementation.) |

Each algorithm file contains comments marking exactly where the core logic
of that algorithm is implemented (flag setting, victim/turn assignment,
and the busy-wait spin condition).

## How to Compile

From the project directory, run:

```
javac *.java
```

This compiles all source files, including `Main.java` and all three lock
implementations.

## How to Run

```
java Main
```

This will run each algorithm in turn (LockOne, then LockTwo, then
Peterson's Lock), using two threads that each increment a shared counter
100,000 times. For each algorithm, the program prints:

- The expected final counter value (200,000, if mutual exclusion holds
  perfectly and no updates are lost).
- The actual final counter value observed.
- Or, if the threads do not finish within 5 seconds, a message reporting
  that the algorithm deadlocked.

## Expected Behaviour

- **LockOne** is expected to **deadlock**. Its known failure mode occurs
  when both threads set their own flag and then check the other thread's
  flag at roughly the same time (lockstep execution) — each ends up
  waiting for the other to clear a flag that never gets cleared.

- **LockTwo** is expected to **deadlock**. Its known failure mode occurs
  when one thread runs before the other has started — the first thread
  sets itself as the "victim" and immediately waits for that value to
  change, but there is no other thread yet to change it.

- **Peterson's Lock** is expected to run to completion **without
  deadlocking**, and the final counter value should equal the expected
  value (200,000). Peterson's Lock combines the flag mechanism from
  LockOne with the turn/victim mechanism from LockTwo, which resolves
  both of the failure modes described above.

These deadlocks for LockOne and LockTwo are not implementation bugs —
they are the intended demonstration of *why* naive locking algorithms
fail, which Peterson's Lock is designed to fix.