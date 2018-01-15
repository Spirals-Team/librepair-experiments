package com.codahale.metrics;

@Deprecated
public interface Reservoir {

    int size();

    void update(long value);

    Snapshot getSnapshot();

    class Adapter implements io.dropwizard.metrics5.Reservoir {

        private final Reservoir reservoir;

        public Adapter(Reservoir reservoir) {
            this.reservoir = reservoir;
        }

        @Override
        public int size() {
            return reservoir.size();
        }

        @Override
        public void update(long value) {
            reservoir.update(value);
        }

        @Override
        public io.dropwizard.metrics5.Snapshot getSnapshot() {
            return new Snapshot.ForwardAdapter(reservoir.getSnapshot());
        }
    }
}
