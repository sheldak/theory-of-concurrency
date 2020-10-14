package semaphores.binary;

public class Counter {
    private final Semaphore semaphore = new Semaphore();

    private int counter;

    public Counter() {
        counter = 0;
    }

    public void increment() {
        semaphore.acquire();
        counter++;
        semaphore.release();
    }

    public void decrement() {
        semaphore.acquire();
        counter--;
        semaphore.release();
    }

    public int getCounter() {
        return counter;
    }
}
