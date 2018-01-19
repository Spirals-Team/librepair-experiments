package backend.entity;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import sun.awt.SunHints;

import java.time.LocalDateTime;
public class Pulse {

    private int value;

    @Override
    public boolean equals(Object obj) {
        return value==((Pulse)obj).getValue();
    }

    public Pulse(int value) {

        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
