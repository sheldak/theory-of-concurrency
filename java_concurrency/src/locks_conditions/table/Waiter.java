package locks_conditions.table;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private boolean tableIsFree;
    private int pairAtTheTable;
    private final List<Integer> alreadyHere = new ArrayList<>();

    private final Lock lock = new ReentrantLock();
    private final List<Condition> isSecondPerson = new ArrayList<>();
    private final Condition isFreeTable = lock.newCondition();

    public Waiter() {
        tableIsFree = true;
        pairAtTheTable = -1;

        for (int i=0; i<Config.PAIRS; i++) {
            alreadyHere.add(0);

            isSecondPerson.add(lock.newCondition());
        }
    }

    public void getTable(int pairID, int customerID) {
        lock.lock();
        alreadyHere.set(pairID, alreadyHere.get(pairID)+1);

        try {
            while (alreadyHere.get(pairID) != 2) {
                isSecondPerson.get(pairID).await();
            }

            while (!tableIsFree && pairAtTheTable != pairID) {
                isFreeTable.await();
            }

            isSecondPerson.get(pairID).signal();
            pairAtTheTable = pairID;
            System.out.printf("[Customer %d (Pair %d)]: sat at the table\n", customerID, pairID);

            tableIsFree = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void releaseTable(int pairID, int customerID) {
        lock.lock();
        alreadyHere.set(pairID, alreadyHere.get(pairID)-1);

        System.out.printf("[Customer %d (Pair %d)]: got up from the table\n", customerID, pairID);

        if (alreadyHere.get(pairID) == 0) {
            System.out.println("[Waiter]: table is free");
            tableIsFree = true;
            isFreeTable.signal();
        }

        lock.unlock();
    }
}
