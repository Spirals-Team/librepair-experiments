package com.booking.replication.commons.checkpoint;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Checkpoint implements Serializable, Comparable<Checkpoint> {
    private long serverId;
    private GTID gtid;
    private Binlog binlog;

    public Checkpoint() {
    }

    public Checkpoint(long serverId, GTID gtid, Binlog binlog) {
        this.serverId = serverId;
        this.gtid = gtid;
        this.binlog = binlog;
    }

    public long getServerId() {
        return this.serverId;
    }

    public Binlog getBinlog() {
        return this.binlog;
    }

    public GTID getGTID() {
        return this.gtid;
    }

    @Override
    public int compareTo(Checkpoint checkpoint) {
        int comparison = 0;

        if (checkpoint != null) {
            if (this.gtid != null &&  checkpoint.gtid != null) {
                comparison = this.gtid.compareTo(checkpoint.gtid);
            } else if (this.gtid != null) {
                comparison = Integer.MAX_VALUE;
            } else if (checkpoint.gtid != null){
                comparison = Integer.MIN_VALUE;
            }

            if (comparison == 0 && this.serverId == checkpoint.serverId) {
                if (this.binlog != null && checkpoint.binlog != null) {
                    comparison = this.binlog.compareTo(checkpoint.binlog);
                } else if (this.binlog != null) {
                    comparison = Integer.MAX_VALUE;
                } else if (checkpoint.binlog != null) {
                    comparison = Integer.MIN_VALUE;
                }
            }
        } else {
            comparison = Integer.MAX_VALUE;
        }

        return comparison;
    }

    @Override
    public boolean equals(Object checkpoint) {
        if (Checkpoint.class.isInstance(checkpoint)) {
            return this.compareTo(Checkpoint.class.cast(checkpoint)) == 0;
        } else {
            return false;
        }
    }
}
