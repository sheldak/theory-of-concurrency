package mandelbrot_set;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import static mandelbrot_set.Config.*;


public class Mandelbrot extends JFrame {

    private final BufferedImage I;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(500, 250, IMAGE_WIDTH, IMAGE_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public void doTask(int taskID) {
        for (int pixel = taskID; pixel < IMAGE_WIDTH * IMAGE_HEIGHT; pixel += TASKS) {
            int x = pixel % getWidth();
            int y = pixel / getWidth();

            double zx, zy, cX, cY, tmp;
            zx = zy = 0;
            cX = (x - X_OFFSET) / ZOOM;
            cY = (y - Y_OFFSET) / ZOOM;
            int currIteration = ITERATIONS;
            while (zx * zx + zy * zy < 4 && currIteration > 0) {
                tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                currIteration--;
            }
            I.setRGB(x, y, currIteration | (currIteration << 8));
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
