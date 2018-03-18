package co.spraybot.messagecodecs;

import co.spraybot.HardDriveSector;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class HardDriveSectorCodec extends BaseCodec<HardDriveSector, HardDriveSector> {
    @Override
    public void encodeToWire(Buffer buffer, HardDriveSector hardDriveSector) {
        JsonObject json = new JsonObject();
        json.put("key", hardDriveSector.identifier());
        json.put("value", hardDriveSector.cellContents());

        String jsonStr = json.encode();
        int length = jsonStr.getBytes().length;

        buffer.appendInt(length);
        buffer.appendString(jsonStr);
    }

    @Override
    public HardDriveSector decodeFromWire(int position, Buffer buffer) {
        int start = position + 4;
        int length = buffer.getInt(position) + 4;
        String data = buffer.getString(start, length);
        JsonObject json = new JsonObject(data);
        return new HardDriveSector() {
            @Override
            public String identifier() {
                return json.getString("key");
            }

            @Override
            public String cellContents() {
                return json.getString("value");
            }
        };
    }
}
