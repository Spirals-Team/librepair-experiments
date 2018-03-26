package com.walkertribe.ian.world;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.walkertribe.ian.Context;
import com.walkertribe.ian.enums.ObjectType;
import com.walkertribe.ian.model.Model;
import com.walkertribe.ian.util.BoolState;
import com.walkertribe.ian.util.TextUtil;

/**
 * Base implementation for all ArtemisObjects.
 */
public abstract class BaseArtemisObject implements ArtemisObject {
	/**
	 * Puts the given int property into the indicated map, unless its value is
	 * equal to unspecifiedValue.
	 */
	public static void putProp(SortedMap<String, Object> props, String label,
			int value, int unspecifiedValue) {
		if (value == unspecifiedValue) {
			return;
		}

		props.put(label, Integer.valueOf(value));
	}

	/**
	 * Puts the given float property into the indicated map, unless its value
	 * is equal to unspecifiedValue.
	 */
	public static void putProp(SortedMap<String, Object> props, String label,
			float value, float unspecifiedValue) {
		if (value == unspecifiedValue) {
			return;
		}

		props.put(label, Float.valueOf(value));
	}

	/**
	 * Puts the given BoolState property into the indicated map, unless the
	 * given value is null or BoolState.UNKNOWN.
	 */
	public static void putProp(SortedMap<String, Object> props, String label,
			BoolState value) {
		if (!BoolState.isKnown(value)) {
			return;
		}

		props.put(label, value);
	}

	/**
	 * Puts the given Object property into the indicated map, unless the given
	 * value is null.
	 */
	public static void putProp(SortedMap<String, Object> props, String label,
			Object value) {
		if (value == null) {
			return;
		}

		props.put(label, value);
	}

	protected final int mId;
    public CharSequence mName;
    private float mX = Float.MIN_VALUE;
    private float mY = Float.MIN_VALUE;
    private float mZ = Float.MIN_VALUE;
    private CharSequence mRace;
    private CharSequence mArtemisClass;
    private CharSequence mIntelLevel1;
    private CharSequence mIntelLevel2;
    private SortedMap<String, byte[]> unknownProps;

    public BaseArtemisObject(int objId) {
        mId = objId;
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public Model getModel(Context ctx) {
    	ObjectType type = getType();
    	return type != null ? type.getModel(ctx) : null;
    }

    @Override
    public float getScale(Context ctx) {
    	ObjectType type = getType();
    	return type != null ? type.getScale() : 0;
    }

    @Override
    public CharSequence getName() {
        return mName;
    }

    public void setName(CharSequence name) {
    	mName = name;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public void setX(float mX) {
        this.mX = mX;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public void setY(float y) {
        mY = y;
    }

    @Override
    public float getZ() {
        return mZ;
    }

    @Override
    public void setZ(float z) {
        mZ = z;
    }

    /**
     * The race of this object, as determined from a science scan.
     * Unspecified: null
     */
    @Override
    public CharSequence getRace() {
    	return mRace;
    }

    public void setRace(CharSequence race) {
    	mRace = race;
    }

    /**
     * The object's class, as determined from a science scan. This property is referred to as
     * "artemisClass" to avoid colliding with Object.getClass().
     * Unspecified: null
     */
    @Override
    public CharSequence getArtemisClass() {
    	return mArtemisClass;
    }

    public void setArtemisClass(CharSequence artemisClass) {
    	mArtemisClass = artemisClass;
    }

    /**
     * The level 1 scan intel for this object.
     * Unspecified: null
     */
    @Override
    public CharSequence getIntelLevel1() {
    	return mIntelLevel1;
    }

    public void setIntelLevel1(CharSequence intelLevel1) {
    	mIntelLevel1 = intelLevel1;
    }

    /**
     * The level 2 scan intel for this object.
     * Unspecified: null
     */
    @Override
    public CharSequence getIntelLevel2() {
    	return mIntelLevel2;
    }

    public void setIntelLevel2(CharSequence intelLevel2) {
    	mIntelLevel2 = intelLevel2;
    }

    @Override
    public boolean hasPosition() {
    	return mX != Float.MIN_VALUE && mY != Float.MIN_VALUE && mZ != Float.MIN_VALUE;
    }

    @Override
    public float distance(ArtemisObject obj) {
    	if (!hasPosition() || !obj.hasPosition()) {
    		throw new RuntimeException("Can't compute distance if both objects don't have a position");
    	}

    	float dX = obj.getX() - mX;
    	float dY = obj.getY() - mY;
    	float dZ = obj.getZ() - mZ;
    	return (float) Math.sqrt(dX * dX + dY * dY + dZ * dZ);
    }

    @Override
    public void updateFrom(ArtemisObject obj) {
		CharSequence name = obj.getName();

		if (name != null) {
            mName = name;
        }

        float x = obj.getX();
        float y = obj.getY();
        float z = obj.getZ();

        if (x != Float.MIN_VALUE) {
        	mX = x;
        }

        if (y != Float.MIN_VALUE) {
        	mY = y;
        }

        if (z != Float.MIN_VALUE) {
        	mZ = z;
        }

        CharSequence str = obj.getRace();

        if (str != null) {
        	setRace(str);
        }

        str = obj.getArtemisClass();

        if (str != null) {
        	setArtemisClass(str);
        }

        str = obj.getIntelLevel1();

        if (str != null) {
        	setIntelLevel1(str);
        }

        str = obj.getIntelLevel2();

        if (str != null) {
        	setIntelLevel2(str);
        }

        BaseArtemisObject cast = (BaseArtemisObject) obj;
        SortedMap<String, byte[]> unknown = cast.getUnknownProps();

        if (unknown != null && !unknown.isEmpty()) {
        	if (unknownProps == null) {
        		unknownProps = new TreeMap<String, byte[]>();
        	}

        	unknownProps.putAll(unknown);
        }
    }

    @Override
    public final SortedMap<String, byte[]> getUnknownProps() {
    	return unknownProps;
    }

    @Override
    public final void setUnknownProps(SortedMap<String, byte[]> unknownProps) {
    	this.unknownProps = unknownProps;
    }

    @Override
    public final SortedMap<String, Object> getProps() {
    	SortedMap<String, Object> props = new TreeMap<String, Object>();
    	appendObjectProps(props);
    	return props;
    }

    @Override
    public final String toString() {
    	SortedMap<String, Object> props = getProps();
    	StringBuilder b = new StringBuilder();

    	for (Map.Entry<String, Object> entry : props.entrySet()) {
    		b.append("\n\t").append(entry.getKey()).append(": ");
    		Object value = entry.getValue();

    		if (value instanceof byte[]) { 
    			b.append(TextUtil.byteArrayToHexString((byte[]) value));
    		} else {
    			b.append(value);
    		}
    	}

    	return b.toString();
    }

    /**
     * Appends this object's properties to the given map. Unspecified values
     * should be omitted. Subclasses must always call the superclass's
     * implementation of this method.
     */
	protected void appendObjectProps(SortedMap<String, Object> props) {
    	props.put("ID", Integer.valueOf(mId));
    	putProp(props, "Name", mName);
    	putProp(props, "Object type", getType());
    	putProp(props, "X", mX, Float.MIN_VALUE);
    	putProp(props, "Y", mY, Float.MIN_VALUE);
    	putProp(props, "Z", mZ, Float.MIN_VALUE);
    	putProp(props, "Race", mRace);
    	putProp(props, "Class", mArtemisClass);
    	putProp(props, "Level 1 intel", mIntelLevel1);
    	putProp(props, "Level 2 intel", mIntelLevel2);

    	if (unknownProps != null) {
        	props.putAll(unknownProps);
    	}
    }

	/**
	 * Returns true if this object contains any data.
	 */
	protected boolean hasData() {
		return  mName != null ||
				mX != Float.MIN_VALUE ||
				mY != Float.MIN_VALUE ||
				mZ != Float.MIN_VALUE;
	}

    @Override
    public boolean equals(Object other) {
    	if (this == other) {
    		return true;
    	}

    	if (!(other instanceof ArtemisObject)) {
            return false;
        }
        
        return mId == ((ArtemisObject) other).getId();
    }

    @Override
    public int hashCode() {
        return mId;
    }
}