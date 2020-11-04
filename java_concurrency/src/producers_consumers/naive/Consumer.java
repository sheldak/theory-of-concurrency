package producers_consumers.naive;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer implements Runnable {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();

        List<String> times = new ArrayList<>();

        try {
            for (int i = 0; i < Config.CONSUMERS_ATTEMPTS; i++) {
                int amount = Math.abs(random.nextInt()) % (Config.BUFFER_SIZE / 2 - 1) + 1;

                long startTime = System.nanoTime();
                if (buffer.get(amount)) {
                    times.add(String.format("C %d %d\n", amount, System.nanoTime() - startTime));
                } else {
                    break;
                }

                Thread.sleep(Math.abs(random.nextInt()) % 30);
            }

            Writer.write(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
