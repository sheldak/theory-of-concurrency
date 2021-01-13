package csp.not_ordered;
import org.jcsp.lang.*;

public class Launcher {
    public static void main(String[] args) {
        One2OneChannelSymmetricInt[] toBufferChannels = new One2OneChannelSymmetricInt[Config.BUFFER_SIZE];
        One2OneChannelSymmetricInt[] fromBufferChannels = new One2OneChannelSymmetricInt[Config.BUFFER_SIZE];
        CSProcess[] procList = new CSProcess[Config.BUFFER_SIZE + 2];

        for (int i=0; i<Config.BUFFER_SIZE; i++) {
            toBufferChannels[i] = Channel.one2oneSymmetricInt();
            fromBufferChannels[i] = Channel.one2oneSymmetricInt();
            procList[i] = new BufferElement(i, toBufferChannels[i], fromBufferChannels[i]);
        }

        procList[Config.BUFFER_SIZE] = new Producer(toBufferChannels);
        procList[Config.BUFFER_SIZE + 1] = new Consumer(fromBufferChannels);

        Parallel par = new Parallel(procList);
        par.run();
    }
}
