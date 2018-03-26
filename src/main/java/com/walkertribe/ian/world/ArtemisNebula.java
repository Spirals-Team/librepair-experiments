package com.walkertribe.ian.world;

import java.util.SortedMap;

import com.walkertribe.ian.enums.ObjectType;

/**
 * Nebulae
 * @author rjwut
 */
public class ArtemisNebula extends BaseArtemisObject {
    private float mRed = -1;
    private float mGreen = -1;
    private float mBlue = -1;

	public ArtemisNebula(int objId) {
        super(objId);
    }

	@Override
	public ObjectType getType() {
		return ObjectType.NEBULA;
	}

    /**
     * The red channel value for the color.
     * Unspecified: -1
     */
    public float getRed() {
    	return mRed;
    }

    public void setRed(float red) {
    	mRed = red;
    }

    /**
     * The green channel value for the color.
     * Unspecified: -1
     */
    public float getGreen() {
    	return mGreen;
    }

    public void setGreen(float green) {
    	mGreen = green;
    }

    /**
     * The blue channel value for the color.
     * Unspecified: -1
     */
    public float getBlue() {
    	return mBlue;
    }

    public void setBlue(float blue) {
    	mBlue = blue;
    }

    @Override
    public void updateFrom(ArtemisObject other) {
        super.updateFrom(other);

        if (other instanceof ArtemisNebula) {
            ArtemisNebula n = (ArtemisNebula) other;

            if (n.mRed != -1) {
            	mRed = n.mRed;
            }

            if (n.mGreen != -1) {
            	mGreen = n.mGreen;
            }

            if (n.mBlue != -1) {
            	mBlue = n.mBlue;
            }
        }
    }

    @Override
	public void appendObjectProps(SortedMap<String, Object> props) {
    	super.appendObjectProps(props);
    	putProp(props, "Red", mRed, -1);
    	putProp(props, "Green", mGreen, -1);
    	putProp(props, "Blue", mBlue, -1);
    }
}