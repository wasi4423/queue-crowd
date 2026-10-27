import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueManager {
    private final List<QueueToken> queue = new ArrayList<>();
    private int nextTokenNumber = 1;
    private final int averageServiceMinutes = 5;
    private final int activeCounters = 2;

    public QueueToken addCustomer(String name, String service) {
        QueueToken token = new QueueToken(nextTokenNumber++, new Customer(name, service));
        queue.add(token);
        return token;
    }
    public QueueToken callNextCustomer() {
        return queue.isEmpty() ? null : queue.remove(0);
    }
    public List<QueueToken> getQueue() {
        return Collections.unmodifiableList(new ArrayList<>(queue));
    }
    public int getQueueSize() { return queue.size(); }
    public int getWaitingTime(int tokenNumber) {
        for (int i = 0; i < queue.size(); i++) {
            if (queue.get(i).getTokenNumber() == tokenNumber) {
                return (int) Math.ceil((double) i * averageServiceMinutes / activeCounters);
            }
        }
        return -1;
    }
}
