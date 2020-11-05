package mandelbrot_set;


public class Config {
    public static final int WORKERS = 8;
    public static final int TASKS = 80;

    public static final int ITERATIONS = 570;
    public static final int IMAGE_WIDTH = 800;
    public static final int IMAGE_HEIGHT = 600;
    public static final int X_OFFSET = 500;
    public static final int Y_OFFSET = 300;
    public static final double ZOOM = 200;

    public static final String TABLE_PATH =
            "/home/sheldak/dev/many/theory_of_concurrency/java_concurrency/src/mandelbrot_set/times.csv";
}
