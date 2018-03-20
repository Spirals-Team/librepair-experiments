package net.runelite.mixins;

import net.runelite.api.mixins.Copy;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Replace;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSFrames;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSSequence;

@Mixin(RSSequence.class)
public abstract class RSSequenceMixin implements RSSequence
{
	@Shadow("clientInstance")
	private static RSClient client;

	@Copy("applyTransformations")
	public abstract RSModel rs$applyTransformations(RSModel model, int actionFrame, RSSequence poseSeq, int poseFrame);

	@Replace("applyTransformations")
	public RSModel rl$applyTransformations(RSModel model, int actionFrame, RSSequence poseSeq, int poseFrame)
	{
		if (actionFrame < 0)
		{
			int packed = actionFrame ^ Integer.MIN_VALUE;
			actionFrame = packed & 0xFFFF;
		}
		if (poseFrame < 0)
		{
			int packed = poseFrame ^ Integer.MIN_VALUE;
			poseFrame = packed & 0xFFFF;
		}
		return rs$applyTransformations(model, actionFrame, poseSeq, poseFrame);
	}

	@Copy("transformActorModel")
	public abstract RSModel rs$transformActorModel(RSModel model, int frameIdx);

	@Replace("transformActorModel")
	public RSModel rl$transformActorModel(RSModel model, int frame)
	{
		if (frame < 0)
		{
			int packed = frame ^ Integer.MIN_VALUE;
			int interval = packed >> 16;
			frame = packed & 0xFFFF;
			int nextFrame = frame + 1;
			if (nextFrame >= getFrameIDs().length)
			{
				nextFrame = -1;
			}
			int[] frameIds = getFrameIDs();
			int frameId = frameIds[frame];
			RSFrames frames = client.getFrames(frameId >> 16);
			int frameIdx = frameId & 0xFFFF;

			int nextFrameIdx = -1;
			RSFrames nextFrames = null;
			if (nextFrame != -1)
			{
				int nextFrameId = frameIds[nextFrame];
				nextFrames = client.getFrames(nextFrameId >> 16);
				nextFrameIdx = nextFrameId & 0xFFFF;
			}

			if (frames == null)
			{
				return model.toSharedModel(true);
			}
			else
			{
				RSModel animatedModel = model.toSharedModel(!frames.getFrames()[frameIdx].isShowing());
				animatedModel.interpolateFrames(frames, frameIdx, nextFrames, nextFrameIdx, interval,
						getFrameLenths()[frame]);
				return animatedModel;
			}
		}
		else
		{
			return rs$transformActorModel(model, frame);
		}
	}

	@Copy("transformObjectModel")
	public abstract RSModel rs$transformObjectModel(RSModel model, int frame, int rotation);

	@Replace("transformObjectModel")
	public RSModel rl$transformObjectModel(RSModel model, int frame, int rotation)
	{
		if (frame < 0)
		{
			int packed = frame ^ Integer.MIN_VALUE;
			int interval = packed >> 16;
			frame = packed & 0xFFFF;

			int nextFrame = frame + 1;
			if (nextFrame >= getFrameIDs().length)
			{
				nextFrame = -1;
			}
			int[] frameIds = getFrameIDs();
			int frameId = frameIds[frame];
			RSFrames frames = client.getFrames(frameId >> 16);
			int frameIdx = frameId & 0xFFFF;

			int nextFrameIdx = -1;
			RSFrames nextFrames = null;
			if (nextFrame != -1)
			{
				int nextFrameId = frameIds[nextFrame];
				nextFrames = client.getFrames(nextFrameId >> 16);
				nextFrameIdx = nextFrameId & 0xFFFF;
			}

			if (frames == null)
			{
				return model.toSharedModel(true);
			}
			else
			{
				RSModel animatedModel = model.toSharedModel(!frames.getFrames()[frameIdx].isShowing());
				rotation &= 3;
				if (rotation == 1)
				{
					animatedModel.rotateY270Ccw();
				}
				else if (rotation == 2)
				{
					animatedModel.rotateY180Ccw();
				}
				else if (rotation == 3)
				{
					animatedModel.rotateY90Ccw();
				}
				animatedModel.interpolateFrames(frames, frameIdx, nextFrames, nextFrameIdx, interval,
						getFrameLenths()[frame]);
				if (rotation == 1)
				{
					animatedModel.rotateY90Ccw();
				}
				else if (rotation == 2)
				{
					animatedModel.rotateY180Ccw();
				}
				else if (rotation == 3)
				{
					animatedModel.rotateY270Ccw();
				}
				return animatedModel;
			}
		}
		else
		{
			return rs$transformObjectModel(model, frame, rotation);
		}
	}

	@Copy("transformSpotAnimModel")
	public abstract RSModel rs$transformSpotAnimModel(RSModel model, int frame);

	@Replace("transformSpotAnimModel")
	public RSModel rl$transformSpotAnimModel(RSModel model, int frame)
	{
		if (frame < 0)
		{
			int packed = frame ^ Integer.MIN_VALUE;
			int interval = packed >> 16;
			frame = packed & 0xFFFF;
			int nextFrame = frame + 1;
			if (nextFrame >= getFrameIDs().length)
			{
				nextFrame = -1;
			}
			int[] frameIds = getFrameIDs();
			int frameId = frameIds[frame];
			RSFrames frames = client.getFrames(frameId >> 16);
			int frameIdx = frameId & 0xFFFF;

			int nextFrameIdx = -1;
			RSFrames nextFrames = null;
			if (nextFrame != -1)
			{
				int nextFrameId = frameIds[nextFrame];
				nextFrames = client.getFrames(nextFrameId >> 16);
				nextFrameIdx = nextFrameId & 0xFFFF;
			}

			if (frames == null)
			{
				return model.toSharedSpotAnimModel(true);
			}
			else
			{
				RSModel animatedModel = model.toSharedSpotAnimModel(!frames.getFrames()[frameIdx].isShowing());
				animatedModel.interpolateFrames(frames, frameIdx, nextFrames, nextFrameIdx, interval,
						getFrameLenths()[frame]);
				return animatedModel;
			}
		}
		else
		{
			return rs$transformSpotAnimModel(model, frame);
		}
	}
}
