package basics.consumer_producer;

public class Buffer {
    public static final String EMPTY_MESSAGE = "";

    private String message;
    private int takenMessages;

    public Buffer() {
        message = EMPTY_MESSAGE;
        takenMessages = 0;
    }

    public synchronized void put(String message) {
        while(!EMPTY_MESSAGE.equals(this.message)) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        this.message = message;
        notifyAll();
    }

    public synchronized String take() {
        while(EMPTY_MESSAGE.equals(this.message) && !areAllMessagesTaken()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        if (areAllMessagesTaken()) {
            return EMPTY_MESSAGE;
        }

        String message = this.message;
        this.message = EMPTY_MESSAGE;
        takenMessages++;
        notifyAll();

        return message;
    }

    public boolean areAllMessagesTaken() {
        return takenMessages == Config.PRODUCERS * Config.MESSAGES_TO_PRODUCE;
    }
}
