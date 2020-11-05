package mandelbrot_set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static mandelbrot_set.Config.*;


public class Launcher {

    public static void main(String[] args) {
        List<Long> times = new ArrayList<>();

        for (int i=0; i<11; i++) {
            long startTime = System.nanoTime();

            Mandelbrot mandelbrot = new Mandelbrot();

            ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

            Set<Future<?>> set = new HashSet<>();
            for (int taskID = 0; taskID < TASKS; taskID++) {
                Runnable runnable = new DoTaskRunnable(taskID, mandelbrot);
                Future<?> future = pool.submit(runnable);
                set.add(future);
            }

            for (Future<?> future : set) {
                while (true) {
                    if (future.isDone()) {
                        break;
                    }
                }
            }

            long time = System.nanoTime() - startTime;
            if (i > 0) {
                times.add(System.nanoTime() - startTime);
            }

            System.out.println(time);

            if (i == 9) {
                mandelbrot.setVisible(true);
            }
        }

        double mean = Statistics.getMean(times);
        double standardDeviation = Statistics.getStandardDeviation(times, mean);

        Writer.write(mean, standardDeviation);
    }
}
