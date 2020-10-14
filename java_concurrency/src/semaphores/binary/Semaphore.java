package semaphores.binary;

public class Semaphore {
    private boolean locked;

    public Semaphore() {
        locked = false;
    }

    public synchronized void acquire() {
        while (locked) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        locked = true;
    }

    public synchronized void release() {
        locked = false;
        notifyAll();
    }
}
