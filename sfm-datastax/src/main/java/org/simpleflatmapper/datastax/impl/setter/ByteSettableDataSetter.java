package org.simpleflatmapper.datastax.impl.setter;

import com.datastax.driver.core.SettableByIndexData;
import org.simpleflatmapper.datastax.DataHelper;
import org.simpleflatmapper.reflect.Setter;
import org.simpleflatmapper.reflect.primitive.ByteSetter;

public class ByteSettableDataSetter implements Setter<SettableByIndexData, Byte>, ByteSetter<SettableByIndexData> {
    private final int index;

    public ByteSettableDataSetter(int index) {
        this.index = index;
    }

    @Override
    public void setByte(SettableByIndexData target, byte value) throws Exception {
        DataHelper.setByte(index, value, target);
    }

    @Override
    public void set(SettableByIndexData target, Byte value) throws Exception {
        if (value == null) {
            target.setToNull(index);
        } else {
            DataHelper.setByte(index, value, target);
        }
    }
}
