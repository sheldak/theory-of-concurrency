package semaphores.counting;

import java.util.ArrayList;
import java.util.List;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        Shop shop = new Shop(Config.CARTS);

        List<Thread> threads = new ArrayList<>();
        for (int i=0; i<Config.CUSTOMERS; i++) {
            Thread newThread = new Thread(new Customer(i, shop));
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
