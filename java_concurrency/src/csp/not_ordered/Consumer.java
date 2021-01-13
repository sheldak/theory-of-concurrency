package csp.not_ordered;
import org.jcsp.lang.*;

public class Consumer implements CSProcess {
    private final AltingChannelInputInt[] in;

    public Consumer(One2OneChannelSymmetricInt[] channels) {
        in = new AltingChannelInputInt[channels.length];
        for (int i=0; i<channels.length; i++) {
            in[i] = channels[i].in();
        }
    }

    public void run () {
        final Alternative alternative = new Alternative(in);

        for (int i=0; i<Config.PRODUCTS; i++) {
            int index = alternative.fairSelect();

            int item = in[index].read();
            System.out.println("Consuming "+ item);
        }
    }
}
