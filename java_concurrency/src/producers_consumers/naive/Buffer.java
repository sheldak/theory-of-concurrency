package producers_consumers.naive;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int currentElements;

    private final Lock lock = new ReentrantLock();
    private final Condition isEmptyEnough = lock.newCondition();
    private final Condition isFullEnough = lock.newCondition();

    private int putsNumber;
    private int getsNumber;

    public Buffer() {
        currentElements = 0;

        putsNumber = 0;
        getsNumber = 0;
    }

    public boolean put(int amount) {
        lock.lock();

        try {
            while (currentElements + amount > Config.BUFFER_SIZE) {
                if (getsNumber == Config.CONSUMERS * Config.CONSUMERS_ATTEMPTS) {
                    return false;
                }

                isEmptyEnough.await();
            }

            currentElements += amount;
            putsNumber += 1;
            System.out.printf("Put %d; in buffer: %d; puts: %d\n", amount, currentElements, putsNumber);

            isFullEnough.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return true;
    }

    public boolean get(int amount) {
        lock.lock();

        try {
            while (amount > currentElements) {

                if (putsNumber == Config.PRODUCERS * Config.PRODUCERS_ATTEMPTS) {
                    return false;
                }

                isFullEnough.await();
            }

            currentElements -= amount;
            getsNumber += 1;
            System.out.printf("Got %d; in buffer: %d; gets: %d\n", amount, currentElements, getsNumber);

            isEmptyEnough.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return true;
    }
}
