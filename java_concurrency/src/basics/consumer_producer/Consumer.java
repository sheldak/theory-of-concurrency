package basics.consumer_producer;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int index;

    public Consumer(Buffer buffer, int index) {
        this.buffer = buffer;
        this.index = index;
    }

    public void run() {
        while (!buffer.areAllMessagesTaken()) {
            String message = buffer.take();

            if (!Buffer.EMPTY_MESSAGE.equals(message)) {
                System.out.printf("From consumer %d: %s%n", index, message);
            }
        }
    }
}
