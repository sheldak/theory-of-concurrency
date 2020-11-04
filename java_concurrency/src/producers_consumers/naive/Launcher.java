package producers_consumers.naive;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        Writer.init();
        Buffer buffer = new Buffer();

        List<Thread> threads = new ArrayList<>();

        for(int i=0; i < Config.PRODUCERS; i++) {
            threads.add(new Thread(new Producer(buffer)));
        }
        for(int i=0; i < Config.CONSUMERS; i++) {
            threads.add(new Thread(new Consumer(buffer)));
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
