package csp.ordered;

import org.jcsp.lang.*;

public class Producer implements CSProcess {
    private final One2OneChannelInt channel;

    public Producer(One2OneChannelInt channel) {
        this.channel = channel;
    }

    public void run () {
        for (int i = 0; i< Config.PRODUCTS; i++) {
            int item = (int)(Math.random()*100)+1;
            System.out.println("Producing "+ item);

            channel.out().write(item);
        }
    }
}
