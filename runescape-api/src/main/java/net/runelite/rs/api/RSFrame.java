package net.runelite.rs.api;

import net.runelite.mapping.Import;

public interface RSFrame
{
	@Import("skin")
	RSFrameMap getSkin();

	@Import("transformCount")
	int getTransformCount();

	@Import("transformTypes")
	int[] getTransformTypes();

	@Import("translator_x")
	int[] getTranslatorX();

	@Import("translator_y")
	int[] getTranslatorY();

	@Import("translator_z")
	int[] getTranslatorZ();

	@Import("showing")
	boolean isShowing();
}
