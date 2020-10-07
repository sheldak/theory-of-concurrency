package basics.synchronized_counter;

public class Counter {
    private int counter;

    public Counter() {
        counter = 0;
    }

    public synchronized void increment() {
        counter++;
    }

    public synchronized void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }
}
