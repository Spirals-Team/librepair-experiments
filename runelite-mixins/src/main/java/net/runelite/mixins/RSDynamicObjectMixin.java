package net.runelite.mixins;

import net.runelite.api.mixins.Copy;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Replace;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSDynamicObject;
import net.runelite.rs.api.RSModel;

@Mixin(RSDynamicObject.class)
public abstract class RSDynamicObjectMixin implements RSDynamicObject
{
	@Shadow("clientInstance")
	private static RSClient client;

	@Copy("getModel")
	public abstract RSModel rs$getModel();

	@Replace("getModel")
	public RSModel rl$getModel()
	{
		try
		{
			return rs$getModel();
		}
		finally
		{
			if (getAnimFrame() < 0)
			{
				setAnimFrame((getAnimFrame() ^ Integer.MIN_VALUE) & 0xFFFF);
			}
		}
	}

	@FieldHook("animCycleCount")
	@Inject
	public void onAnimCycleCountChanged(int idx)
	{
		boolean fromConstructor = true;
		// hack to see if this got called from the constructor
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		if (stackTrace.length > 1)
		{
			fromConstructor = stackTrace[1].getMethodName().equals("<init>");
		}
		if (!fromConstructor && client.isInterpolateObjectAnimations())
		{
			setAnimFrame(Integer.MIN_VALUE | (client.getGameCycle() - getAnimCycleCount()) << 16 | getAnimFrame());
		}
	}
}
