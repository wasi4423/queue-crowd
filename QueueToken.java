public class QueueToken {
    private final int tokenNumber;
    private final Customer customer;
    public QueueToken(int tokenNumber, Customer customer) {
        this.tokenNumber = tokenNumber; this.customer = customer;
    }
    public int getTokenNumber() { return tokenNumber; }
    public Customer getCustomer() { return customer; }
}
