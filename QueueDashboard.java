import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QueueDashboard extends JFrame {
    private final QueueManager manager;
    private final CrowdPredictor predictor;
    private final JTextField nameField = new JTextField(12);
    private final JTextField serviceField = new JTextField(12);
    private final JTextField tokenField = new JTextField(6);
    private final JLabel crowdLabel = new JLabel("Crowd Level: LOW");
    private final JLabel statusLabel = new JLabel("Welcome! Add a customer to begin.");
    private final DefaultTableModel model = new DefaultTableModel(
        new String[]{"Token", "Customer Name", "Service"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };

    public QueueDashboard(QueueManager manager, CrowdPredictor predictor) {
        this.manager = manager;
        this.predictor = predictor;
        setTitle("Smart Queue & Crowd Prediction System");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Smart Queue & Crowd Prediction System", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 4, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        form.add(new JLabel("Customer Name:")); form.add(nameField);
        form.add(new JLabel("Service:")); form.add(serviceField);
        JButton addButton = new JButton("Add Customer / Get Token");
        JButton callButton = new JButton("Call Next Customer");
        form.add(addButton); form.add(callButton);
        form.add(new JLabel("Token Number:")); form.add(tokenField);

        JTable table = new JTable(model);
        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        center.add(form, BorderLayout.NORTH);
        center.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton waitButton = new JButton("Check Waiting Time");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(waitButton); actions.add(crowdLabel);
        JPanel bottom = new JPanel(new GridLayout(2, 1, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(0, 10, 12, 10));
        bottom.add(actions); bottom.add(statusLabel);

        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCustomer());
        callButton.addActionListener(e -> callNext());
        waitButton.addActionListener(e -> checkWaitingTime());
        refreshQueue();
    }

    private void addCustomer() {
        String name = nameField.getText().trim();
        String service = serviceField.getText().trim();
        if (name.isEmpty() || service.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both customer name and service.",
                "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }
        QueueToken token = manager.addCustomer(name, service);
        statusLabel.setText("Customer added. Token number: " + token.getTokenNumber());
        nameField.setText(""); serviceField.setText("");
        refreshQueue();
    }

    private void callNext() {
        QueueToken token = manager.callNextCustomer();
        if (token == null) {
            JOptionPane.showMessageDialog(this, "The queue is empty.");
            statusLabel.setText("No customers waiting.");
        } else {
            statusLabel.setText("Now serving Token " + token.getTokenNumber()
                + " — " + token.getCustomer().getName());
            JOptionPane.showMessageDialog(this, "Now Serving\nToken: "
                + token.getTokenNumber() + "\nName: " + token.getCustomer().getName());
        }
        refreshQueue();
    }

    private void checkWaitingTime() {
        try {
            int number = Integer.parseInt(tokenField.getText().trim());
            int minutes = manager.getWaitingTime(number);
            statusLabel.setText(minutes < 0
                ? "Token not found in the current queue."
                : "Estimated waiting time for Token " + number + ": " + minutes + " minute(s).");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid numeric token number.",
                "Invalid Token", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshQueue() {
        model.setRowCount(0);
        for (QueueToken token : manager.getQueue()) {
            model.addRow(new Object[]{token.getTokenNumber(),
                token.getCustomer().getName(), token.getCustomer().getService()});
        }
        crowdLabel.setText("Crowd Level: " + predictor.predictCrowd(manager.getQueueSize())
            + " | Waiting: " + manager.getQueueSize());
    }
}
