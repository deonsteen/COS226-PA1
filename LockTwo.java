public class LockTwo implements Lock {
    
    private volatile int victim;

    @Override 
    public void lock()
    {
        int i = Threadid.get();
        victim = i;

        while (victim ==i)
        {
        }
    }

    @Override

    public void unlock() 
    {
        
    }
}
