package csp.ordered;

import org.jcsp.lang.*;

public class Consumer implements CSProcess {
    private final One2OneChannelInt channel;

    public Consumer(One2OneChannelInt channel) {
        this.channel = channel;
    }

    public void run () {
        for (int i = 0; i< Config.PRODUCTS; i++) {
            int item = channel.in().read();
            System.out.println("Consuming "+ item);
        }
    }
}
