import javax.swing.SwingUtilities;

public class SmartQueueSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QueueManager manager = new QueueManager();
            CrowdPredictor predictor = new CrowdPredictor();
            new QueueDashboard(manager, predictor).setVisible(true);
        });
    }
}
