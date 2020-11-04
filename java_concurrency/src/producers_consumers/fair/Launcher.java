package producers_consumers.fair;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        Writer.init();
        Buffer buffer = new Buffer();

        List<Thread> threads = new ArrayList<>();

        for(int id = 0; id < Config.PRODUCERS; id++) {
            threads.add(new Thread(new Producer(id, buffer)));
        }
        for(int id = 0; id < Config.CONSUMERS; id++) {
            threads.add(new Thread(new Consumer(id, buffer)));
        }

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
