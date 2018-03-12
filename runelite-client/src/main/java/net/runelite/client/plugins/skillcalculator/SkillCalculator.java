/*
 * Copyright (c) 2018, Kruithne <kruithne@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.skillcalculator;

import net.runelite.api.Client;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

class SkillCalculator extends JPanel
{
	private Client client;
	private SkillData skillData;
	private List<UIActionSlot> uiActionSlots = new ArrayList<>();
	private UICalculatorInputArea uiInput;

	private CacheSkillData cacheSkillData = new CacheSkillData();
	static CacheItemIcon cacheItemIcon = new CacheItemIcon();

	private int currentLevel = MIN_LEVEL;
	private int currentXP = SkillLevelToXP(currentLevel);
	private int targetLevel = currentLevel + 1;
	private int targetXP = SkillLevelToXP(targetLevel);
	private float xpFactor = 1.0f;

	private static int MIN_LEVEL = 1;
	private static int MAX_LEVEL = 99;
	private static int MAX_XP = 200000000;

	private static DecimalFormat XP_FORMAT = new DecimalFormat("#.#");

	SkillCalculator(Client client, UICalculatorInputArea uiInput)
	{
		this.client = client;
		this.uiInput = uiInput;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createLineBorder(getBackground().brighter()));

		// Register listeners on the input fields..
		uiInput.uiFieldCurrentLevel.addActionListener(e -> onFieldCurrentLevelUpdated());
		uiInput.uiFieldCurrentXP.addActionListener(e -> onFieldCurrentXPUpdated());
		uiInput.uiFieldTargetLevel.addActionListener(e -> onFieldTargetLevelUpdated());
		uiInput.uiFieldTargetXP.addActionListener(e -> onFieldTargetXPUpdated());
	}

	void openCalculator(CalculatorType calculatorType)
	{
		// Load the skill data.
		skillData = cacheSkillData.getSkillData(calculatorType.getDataFile());

		// Clear the item icon cache.
		cacheItemIcon.clear();

		// Reset the XP factor, removing bonuses.
		xpFactor = 1.0f;

		// Update internal skill/XP values.
		currentXP = client.getSkillExperience(calculatorType.getSkill());
		currentLevel = XPToSkillLevel(currentXP);
		targetLevel = EnforceSkillBounds(currentLevel + 1);
		targetXP = SkillLevelToXP(targetLevel);

		// Remove all components (action slots) from this panel.
		removeAll();

		// Add in checkboxes for available skill bonuses.
		renderBonusOptions();

		// Create action slots for the skill actions.
		renderActionSlots();

		// Update the input fields.
		updateInputFields();
	}

	private void renderBonusOptions()
	{
		if (skillData.bonuses != null)
		{
			for (SkillDataBonus bonus : skillData.bonuses)
			{
				JPanel uiOption = new JPanel(new BorderLayout());
				JLabel uiLabel = new JLabel(bonus.name);
				JCheckBox uiCheckbox = new JCheckBox();

				// Adding an empty 8-pixel border on the left gives us nice padding.
				uiOption.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));

				// Adjust XP bonus depending on check-state of the boxes.
				uiCheckbox.addActionListener(e -> adjustXPBonus(uiCheckbox.isSelected(), bonus.value));

				uiOption.add(uiLabel, BorderLayout.WEST);
				uiOption.add(uiCheckbox, BorderLayout.EAST);
				add(uiOption);
			}
		}
	}

	private void renderActionSlots()
	{
		// Wipe the list of references to the slot components.
		uiActionSlots.clear();

		// Create new components for the action slots.
		for (SkillDataEntry action : skillData.actions)
		{
			UIActionSlot slot = new UIActionSlot(action);
			uiActionSlots.add(slot); // Keep our own reference.
			add(slot); // Add component to the panel.
		}

		// Clear the icon cache once we're done making slots.
		cacheItemIcon.clear();

		// Refresh the rendering of this panel.
		revalidate();
		repaint();
	}

	private void calculate()
	{
		for (UIActionSlot slot : uiActionSlots)
		{
			int actionCount = 0;
			int neededXP = targetXP - currentXP;
			double xp = slot.action.xp * xpFactor;

			if (neededXP > 0)
				actionCount = (int) Math.ceil(neededXP / xp);

			slot.setText(XP_FORMAT.format(xp) + "xp - " + NumberFormat.getIntegerInstance().format(actionCount) + (actionCount > 1 ? " actions" : " action"));

			if (currentLevel >= slot.action.level)
				slot.setSlotColor(Color.GREEN);
			else
				slot.setSlotColor(Color.RED);
		}
	}

	private void updateInputFields()
	{
		if (targetXP < currentXP) {
			targetLevel = EnforceSkillBounds(currentLevel + 1);
			targetXP = SkillLevelToXP(targetLevel);
		}

		uiInput.setCurrentLevelInput(currentLevel);
		uiInput.setCurrentXPInput(currentXP);
		uiInput.setTargetLevelInput(targetLevel);
		uiInput.setTargetXPInput(targetXP);
		calculate();
	}

	private void adjustXPBonus(boolean addBonus, float value)
	{
		xpFactor += addBonus ? value : -value;
		calculate();
	}

	private void onFieldCurrentLevelUpdated()
	{
		currentLevel = EnforceSkillBounds(uiInput.getCurrentLevelInput());
		currentXP = SkillLevelToXP(currentLevel);
		updateInputFields();
	}

	private void onFieldCurrentXPUpdated()
	{
		currentXP = EnforceXPBounds(uiInput.getCurrentXPInput());
		currentLevel = XPToSkillLevel(currentXP);
		updateInputFields();
	}

	private void onFieldTargetLevelUpdated()
	{
		targetLevel = EnforceSkillBounds(uiInput.getTargetLevelInput());
		targetXP = SkillLevelToXP(targetLevel);
		updateInputFields();
	}

	private void onFieldTargetXPUpdated()
	{
		targetXP = EnforceXPBounds(uiInput.getTargetXPInput());
		targetLevel = XPToSkillLevel(targetXP);
		updateInputFields();
	}

	private static int EnforceSkillBounds(int input)
	{
		return Math.min(MAX_LEVEL, Math.max(MIN_LEVEL, input));
	}

	private static int EnforceXPBounds(int input)
	{
		return Math.min(MAX_XP, Math.max(0, input));
	}

	private static int SkillLevelToXP(int level)
	{
		double xp = 0;
		level = EnforceSkillBounds(level);

		for (int i = 1; i < level; i++)
			xp += Math.floor(i + 300 * Math.pow(2, i / 7.0));

		return (int) Math.floor(xp / 4);
	}

	private static int XPToSkillLevel(int xp)
	{
		for (int i = 1; i < MAX_LEVEL; i++)
			if (SkillLevelToXP(i) > xp)
				return EnforceSkillBounds(i - 1);

		return MAX_LEVEL;
	}
}