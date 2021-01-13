package csp.ordered;

import org.jcsp.lang.*;

public class Launcher {
    public static void main(String[] args) {
        One2OneChannelInt[] channels = new One2OneChannelInt[Config.BUFFER_SIZE + 1];
        CSProcess[] procList = new CSProcess[Config.BUFFER_SIZE + 2];

        channels[0] = Channel.one2oneInt();
        procList[0] = new Producer(channels[0]);

        for (int i=0; i<Config.BUFFER_SIZE; i++) {
            channels[i+1] = Channel.one2oneInt();
            procList[i+1] = new BufferElement(i, channels[i], channels[i+1]);
        }

        procList[Config.BUFFER_SIZE + 1] = new Consumer(channels[Config.BUFFER_SIZE]);

        Parallel par = new Parallel(procList);
        par.run();
    }
}
