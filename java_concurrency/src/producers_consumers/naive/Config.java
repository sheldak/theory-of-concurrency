package producers_consumers.naive;

public class Config {
    public static final int BUFFER_SIZE = 1000;
    public static final int PRODUCERS = 10;
    public static final int CONSUMERS = 10;
    public static final int PRODUCERS_ATTEMPTS = 500;
    public static final int CONSUMERS_ATTEMPTS = 500;

    public static final String TIME_PATH =
            "/home/sheldak/dev/many/theory_of_concurrency/java_concurrency/src/producers_consumers/naive/times.txt";
}
