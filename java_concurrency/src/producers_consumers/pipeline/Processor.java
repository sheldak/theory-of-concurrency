package producers_consumers.pipeline;

import java.util.Random;

public class Processor implements Runnable {
    private final String name;
    private final int id;

    private final Buffer buffer;
    private int currIndex;

    private final int workTime;

    public Processor(String name, int id, Buffer buffer) {
        this.name = name;
        this.id = id;

        this.buffer = buffer;
        currIndex = 0;

        Random random = new Random();
        workTime = Math.abs(random.nextInt()%100);
    }

    public void run() {
        for (int i=0; i<Config.ITEMS; i++) {
            buffer.process(currIndex, id, name);
            currIndex = (currIndex + 1) % Config.BUFFER_SIZE;

            try {
                Thread.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
