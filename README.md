# Smart Queue & Crowd Prediction System (Java Swing)

## Features
- Add customer and generate a token
- Display current queue
- Call next customer
- Estimate waiting time
- Predict crowd level: LOW, MEDIUM, HIGH

## Requirements
JDK 8 or newer. No external libraries required.

## Run
Open a terminal in `src`, then run:
```bash
javac *.java
java SmartQueueSystem
```

## Classes
1. `Customer` — customer name and service
2. `QueueToken` — token number linked to a customer
3. `QueueManager` — queue operations and waiting-time estimate
4. `CrowdPredictor` — queue-size-based crowd level
5. `QueueDashboard` — graphical user interface
6. `SmartQueueSystem` — starts the app

## Limitations
This is a classroom prototype. Data is stored in memory and disappears when the app closes.
Crowd prediction uses simple thresholds, not AI/ML. Waiting time is an estimate.
