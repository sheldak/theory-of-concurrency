package locks_conditions.printers;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        PrintersMonitor printersMonitor = new PrintersMonitor();

        List<Thread> threads = new ArrayList<>();

        for (int customerID=0; customerID < Config.CUSTOMERS; customerID++) {
            Thread newThread = new Thread(new Customer(customerID, printersMonitor));
            threads.add(newThread);

            newThread.start();
            Thread.sleep(300);
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
