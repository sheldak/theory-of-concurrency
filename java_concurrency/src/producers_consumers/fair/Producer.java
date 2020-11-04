package producers_consumers.fair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer implements Runnable {
    private final int id;

    private final Buffer buffer;

    public Producer(int id, Buffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();

        List<String> times = new ArrayList<>();

        try {
            for (int i = 0; i < Config.PRODUCERS_ATTEMPTS; i++) {
                int amount = Math.abs(random.nextInt()) % (Config.BUFFER_SIZE / 2 - 1) + 1;

                long startTime = System.nanoTime();
                if (buffer.put(id, amount)) {
                    times.add(String.format("P %d %d\n", amount, System.nanoTime() - startTime));
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
