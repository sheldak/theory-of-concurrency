package csp.not_ordered;
import org.jcsp.lang.*;

public class Producer implements CSProcess {
    private final AltingChannelOutputInt[] out;

    public Producer(One2OneChannelSymmetricInt[] channels) {
        out = new AltingChannelOutputInt[channels.length];
        for (int i=0; i<channels.length; i++) {
            out[i] = channels[i].out();
        }
    }

    public void run () {
        final Alternative alternative = new Alternative(out);

        for (int i=0; i<Config.PRODUCTS; i++) {
            int index = alternative.fairSelect();

            int item = (int)(Math.random()*100)+1;
            System.out.println("Producing "+ item);

            out[index].write(item);
        }
    }
}
