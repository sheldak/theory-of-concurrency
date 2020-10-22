package locks_conditions.table;

import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();

        List<Thread> threads = new ArrayList<>();

        for (int pairID = 0; pairID < Config.PAIRS; pairID++) {
            threads.add(new Thread(new Customer(pairID, 2*pairID, waiter)));
            threads.add(new Thread(new Customer(pairID, 2*pairID + 1, waiter)));
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
