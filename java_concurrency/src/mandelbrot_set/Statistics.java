package mandelbrot_set;

import java.util.List;

public class Statistics {
    public static double getMean(List<Long> times) {
        double mean = 0;
        for (Long time : times) {
            mean += (double) time;
        }

        return (mean / times.size()) / 1_000_000_000;
    }

    public static double getStandardDeviation(List<Long> times, double mean) {
        double deviation = 0;
        for (Long time : times) {
            deviation += Math.pow((double) time / 1_000_000_000 - mean, 2);
        }

        deviation /= times.size();

        return Math.sqrt(deviation);
    }
}
