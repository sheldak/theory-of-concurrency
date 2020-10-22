package locks_conditions.table;

import java.util.Random;

public class Customer implements Runnable {
    private final int pairID;
    private final int ID;

    private final Waiter waiter;

    public Customer(int pairID, int ID, Waiter waiter) {
        this.pairID = pairID;
        this.ID = ID;

        this.waiter = waiter;
    }

    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(Math.abs(random.nextInt()%10000));

            System.out.printf("[Customer %d (Pair %d)]: arrived\n", ID, pairID);

            waiter.getTable(pairID, ID);
            Thread.sleep(1000);
            waiter.releaseTable(pairID, ID);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
