package semaphores.binary;

public class Launcher {
    public static final int OPERATIONS = 10000000;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable incrementRunnable = () -> {
            for (int i=0; i < OPERATIONS; i++) {
                counter.increment();
            }
        };
        Thread incrementThread = new Thread(incrementRunnable);

        Runnable decrementRunnable = () -> {
            for (int i=0; i < OPERATIONS; i++) {
                counter.decrement();
            }
        };
        Thread decrementThread = new Thread(decrementRunnable);

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        System.out.println(counter.getCounter());
    }
}
