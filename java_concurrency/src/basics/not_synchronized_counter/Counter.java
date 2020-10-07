package basics.not_synchronized_counter;

public class Counter {
    private int counter;

    public Counter() {
        counter = 0;
    }

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }
}
