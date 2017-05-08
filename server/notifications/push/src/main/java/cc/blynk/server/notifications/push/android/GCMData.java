package cc.blynk.server.notifications.push.android;

/**
 * The Blynk Project.
 * Created by Dmitriy Dumanskiy.
 * Created on 06.05.17.
 */
public class GCMData {

    private final String message;
    private final int dashId;
    private final long ts;

    public GCMData(String message, int dashId, long ts) {
        this.message = message;
        this.dashId = dashId;
        this.ts = ts;
    }

}
