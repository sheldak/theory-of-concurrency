package locks_conditions.printers;

import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor {
    private final Lock lock = new ReentrantLock();
    private final Condition isFreePrinter = lock.newCondition();

    private final Stack<Integer> freePrinters = new Stack<>();

    public PrintersMonitor() {
        for (int printedID=0; printedID < Config.PRINTERS; printedID++) {
            freePrinters.push(printedID);
        }
    }

    public int reserve() {
        int printerID = -1;

        lock.lock();
        try {
            while (freePrinters.empty()) {
                isFreePrinter.await();
            }

            printerID = freePrinters.pop();
            System.out.printf("[Monitor]: printer %d is reserved\n", printerID);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return printerID;
    }

    public void release(int printerID) {
        lock.lock();

        freePrinters.push(printerID);
        isFreePrinter.signal();
        System.out.printf("[Monitor]: printer %d is released\n", printerID);

        lock.unlock();
    }
}
