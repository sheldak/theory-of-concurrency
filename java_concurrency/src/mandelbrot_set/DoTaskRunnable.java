package mandelbrot_set;


public class DoTaskRunnable implements Runnable {
    private final int id;
    private final Mandelbrot mandelbrot;

    public DoTaskRunnable(int id, Mandelbrot mandelbrot) {
        this.id = id;
        this.mandelbrot = mandelbrot;
    }

    public void run() {
        mandelbrot.doTask(id);
    }
}
