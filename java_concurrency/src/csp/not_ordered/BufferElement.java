package csp.not_ordered;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelSymmetricInt;

public class BufferElement implements CSProcess {
    private final int id;
    private final One2OneChannelSymmetricInt toBufferChannel;
    private final One2OneChannelSymmetricInt fromBufferChannel;

    public BufferElement(int id, One2OneChannelSymmetricInt toBufferChannel, One2OneChannelSymmetricInt fromBufferChannel) {
        this.id = id;
        this.toBufferChannel = toBufferChannel;
        this.fromBufferChannel = fromBufferChannel;
    }

    public void run () {
        while (true) {
            int item = toBufferChannel.in().read();
            System.out.printf("[%d]: %d in buffer\n", id, item);

            fromBufferChannel.out().write(item);
            System.out.printf("[%d]: %d taken from buffer\n", id, item);
        }
    }
}
