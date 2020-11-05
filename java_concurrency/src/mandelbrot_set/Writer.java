package mandelbrot_set;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static mandelbrot_set.Config.*;

public class Writer {
    public static void write(double mean, double standardDeviation) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TABLE_PATH, true))) {
            writer.write(String.format("%d, %d, %f, %f\n", WORKERS, TASKS, mean, standardDeviation));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
