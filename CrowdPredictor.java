public class CrowdPredictor {
    public String predictCrowd(int queueSize) {
        if (queueSize <= 5) return "LOW";
        if (queueSize <= 15) return "MEDIUM";
        return "HIGH";
    }
}
