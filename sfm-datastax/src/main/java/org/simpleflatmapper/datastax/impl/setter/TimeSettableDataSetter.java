package org.simpleflatmapper.datastax.impl.setter;

import com.datastax.driver.core.SettableByIndexData;
import org.simpleflatmapper.datastax.DataHelper;
import org.simpleflatmapper.reflect.Setter;
import org.simpleflatmapper.reflect.primitive.LongSetter;

public class TimeSettableDataSetter implements Setter<SettableByIndexData, Long>, LongSetter<SettableByIndexData> {
    private final int index;

    public TimeSettableDataSetter(int index) {
        this.index = index;
    }

    @Override
    public void setLong(SettableByIndexData target, long value) throws Exception {
        DataHelper.setTime(index, value, target);
    }

    @Override
    public void set(SettableByIndexData target, Long value) throws Exception {
        if (value == null) {
            target.setToNull(index);
        } else {
            DataHelper.setTime(index, value, target);
        }
    }
}
