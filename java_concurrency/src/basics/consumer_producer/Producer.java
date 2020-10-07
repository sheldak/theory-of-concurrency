package basics.consumer_producer;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final int index;

    public Producer(Buffer buffer, int index) {
        this.buffer = buffer;
        this.index = index;
    }

    public void run() {
        for(int i=0;  i<Config.MESSAGES_TO_PRODUCE; i++) {
            buffer.put(String.format(
                    "Message number %d (from producer %d)", index * Config.MESSAGES_TO_PRODUCE + i, index));
        }
    }
}
