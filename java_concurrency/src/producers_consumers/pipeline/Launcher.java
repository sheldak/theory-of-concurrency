package producers_consumers.pipeline;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        List<Thread> threads = new ArrayList<>();

        threads.add(new Thread(new Processor("Producer", 0, buffer)));
        for(int id=1; id <= Config.PROCESSORS; id++) {
            threads.add(new Thread(new Processor(String.format("Processor %d", id), id, buffer)));
        }
        threads.add(new Thread(new Processor("Consumer", Config.PROCESSORS+1, buffer)));

        threads.forEach(Thread::start);

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
