package semaphores.counting;

public class Semaphore {
    private int counter;

    public Semaphore(int counterOnStart) {
        counter = counterOnStart;
    }

    public synchronized void acquire() {
        while (counter == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        counter--;
    }

    public synchronized void release() {
        counter++;
        notifyAll();
    }
}
