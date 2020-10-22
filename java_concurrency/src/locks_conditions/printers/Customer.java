package locks_conditions.printers;

public class Customer implements Runnable {
    private final int ID;
    private final PrintersMonitor printersMonitor;

    public Customer(int ID, PrintersMonitor printersMonitor) {
        this.ID = ID;
        this.printersMonitor = printersMonitor;
    }

    public void run() {
        for (int printID=0; printID < Config.PRINTS; printID++) {
            preparePrint(printID);

            System.out.printf("[Customer %d]: attempts to reserve printer\n", ID);
            int printerID = printersMonitor.reserve();

            print(printID, printerID);
            printersMonitor.release(printerID);
        }
    }

    public void preparePrint(int printID) {
        System.out.printf("[Customer %d]: prepares their print %d\n", ID, printID);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void print(int printID, int printerID) {
        System.out.printf("[Customer %d]: prints their print %d on printer %d\n", ID, printID, printerID);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
