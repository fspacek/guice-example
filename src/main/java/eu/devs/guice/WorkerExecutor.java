package eu.devs.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Singleton
public class WorkerExecutor {

    private final PeriodicWorker worker;

    @Inject
    public WorkerExecutor(PeriodicWorker worker) {
        this.worker = worker;
    }

    public void start() {
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(worker, 1, 1, TimeUnit.SECONDS);
    }
}