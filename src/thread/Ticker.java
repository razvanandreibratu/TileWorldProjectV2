package thread;
import environment.Environment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Ticker {
    private final Environment environment;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private boolean running = true;
    private boolean ticked = false;
    private int agentsCounter = 0;

    public Ticker(Environment environment) {
        this.environment = environment;
    }

    public void stop() {
        running = false;
        scheduler.shutdown();
    }

    public synchronized void tick(String threadName) {
        ticked = true;
        agentsCounter = 0;
        notifyAll();
        System.out.println(threadName + "Tick.");
    }

    public synchronized void action() {
        while (!ticked || agentsCounter < environment.getAgentList().size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        agentsCounter++;
        if (agentsCounter == environment.getAgentList().size()) {
            ticked = false;
            notifyAll();
        }
    }

    public Boolean running() {
        return running;
    }

}