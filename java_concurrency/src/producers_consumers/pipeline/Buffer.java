package producers_consumers.pipeline;

public class Buffer {
    private final int size = Config.BUFFER_SIZE;
    private final int[] buffer = new int[size];

    public Buffer() {
        for (int i=0; i<size; i++) {
            buffer[i] = 0;
        }
    }

    public synchronized void process(int index, int id, String name) {
        try {
            while (buffer[index] != id) {
                wait();
            }

            buffer[index] = (id + 1) % (Config.PROCESSORS + 2);

            System.out.printf("[%s]: ", name);
            printBuffer();

            notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printBuffer() {
        System.out.print("[");

        for (int i=0; i<size; i++) {
            System.out.printf("%d", buffer[i]);

            if (i < size-1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}
