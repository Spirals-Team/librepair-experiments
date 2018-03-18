package co.spraybot.harddrive;

import co.spraybot.HardDrive;
import co.spraybot.HardDriveSector;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;

import java.util.HashMap;
import java.util.Map;

public class TransientHardDrive extends AbstractVerticle implements HardDrive {

    private EventBus eventBus;
    private Map<String, HardDriveSector> neurons = new HashMap<>();

    public TransientHardDrive(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void start() throws Exception {
        super.start();
        eventBus.consumer("spraybot.harddrive", this::handleMessage);
    }

    private void handleMessage(Message message) {
        switch(message.headers().get("op")) {
            case "write":
                Future<Boolean> learned = write((HardDriveSector) message.body());
                learned.setHandler(result -> {
                    message.reply(result.succeeded());
                });
                break;
            case "read":
                Future<HardDriveSector> remembered = read((String) message.body());
                remembered.setHandler(result -> {
                    HardDriveSector hardDriveSector = result.result();
                    String contents = hardDriveSector != null ? hardDriveSector.cellContents() : null;
                    message.reply(contents);
                });
                break;
            case "erase":
                Future<Void> forgot = erase((String) message.body());
                forgot.setHandler(result -> {
                    message.reply(null);
                });
                break;
            case "eraseEverything":
                Future<Void> forgotEverything = eraseEverything();
                forgotEverything.setHandler(result -> {
                    message.reply(null);
                });
                break;
            default:
                message.fail(255, "Did not recognize the operation sent in the Message header");
                break;
        }
    }

    @Override
    public Future<Boolean> write(HardDriveSector hardDriveSector) {
        neurons.put(hardDriveSector.identifier(), hardDriveSector);
        return Future.succeededFuture(true);
    }

    @Override
    public Future<HardDriveSector> read(String neuronIdentifier) {
        return Future.succeededFuture(neurons.get(neuronIdentifier));
    }

    @Override
    public Future<Void> erase(String neuronIdentifier) {
        neurons.remove(neuronIdentifier);
        return Future.succeededFuture();
    }

    @Override
    public Future<Void> eraseEverything() {
        neurons.clear();
        return Future.succeededFuture();
    }

}
