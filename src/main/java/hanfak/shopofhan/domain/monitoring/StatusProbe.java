package hanfak.shopofhan.domain.monitoring;

import java.util.concurrent.Callable;

public interface StatusProbe extends Callable<ProbeResult> {
    ProbeResult probe();
    String name();
    @Override
    default ProbeResult call() {
        return probe();
    }
}
