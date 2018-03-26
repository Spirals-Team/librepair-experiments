package com.walkertribe.ian.world;

import org.junit.Assert;

import com.walkertribe.ian.enums.ObjectType;
import com.walkertribe.ian.util.TestUtil;

public class ArtemisObjectTest {
	public static void assertObject(ArtemisObject obj, int id, ObjectType type, String name, float x, float y,
			float z) {
		Assert.assertEquals(id, obj.getId());
		Assert.assertEquals(type, obj.getType());
		TestUtil.assertToStringEquals(name, obj.getName());
		Assert.assertEquals(x, obj.getX(), TestUtil.EPSILON);
		Assert.assertEquals(y, obj.getY(), TestUtil.EPSILON);
		Assert.assertEquals(z, obj.getZ(), TestUtil.EPSILON);
	}
}
