package producers_consumers.fair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Writer {

    public static void init() {
        try (PrintWriter writer = new PrintWriter(Config.TIME_PATH)) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void write(List<String> times) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Config.TIME_PATH, true))) {
            for (String time : times) {
                writer.write(time);
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
