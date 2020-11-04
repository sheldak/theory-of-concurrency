package producers_consumers.fair;

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

    private int firstProducer;
    private int firstConsumer;

    public Buffer() {
        currentElements = 0;

        putsNumber = 0;
        getsNumber = 0;

        firstProducer = -1;
        firstConsumer = -1;
    }

    public boolean put(int producerID, int amount) {
        lock.lock();

        try {
            while (firstProducer != producerID || currentElements + amount > Config.BUFFER_SIZE) {
                if (firstProducer == -1) {
                    firstProducer = producerID;
                    continue;
                }

                if (getsNumber == Config.CONSUMERS * Config.CONSUMERS_ATTEMPTS) {
                    return false;
                }

                isEmptyEnough.await();
            }

            firstProducer = -1;
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

    public boolean get(int consumerID, int amount) {
        lock.lock();

        try {
            while (firstConsumer != consumerID || amount > currentElements) {
                if (firstConsumer == -1) {
                    firstConsumer = consumerID;
                    continue;
                }

                if (putsNumber == Config.PRODUCERS * Config.PRODUCERS_ATTEMPTS) {
                    return false;
                }

                isFullEnough.await();
            }

            firstConsumer = -1;
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
