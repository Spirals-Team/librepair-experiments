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

import net.runelite.api.Skill;

enum CalculatorType
{
	AGILITY(Skill.AGILITY, "skill_agility.json"),
	CONSTRUCTION(Skill.CONSTRUCTION, "skill_construction.json"),
	COOKING(Skill.COOKING, "skill_cooking.json"),
	CRAFTING(Skill.CRAFTING, "skill_crafting.json"),
	FIREMAKING(Skill.FIREMAKING, "skill_firemaking.json"),
	FISHING(Skill.FISHING, "skill_fishing.json"),
	FLETCHING(Skill.FLETCHING, "skill_fletching.json"),
	HERBLORE(Skill.HERBLORE, "skill_herblore.json"),
	HUNTER(Skill.HUNTER, "skill_hunter.json"),
	MAGIC(Skill.MAGIC, "skill_magic.json"),
	MINING(Skill.MINING, "skill_mining.json"),
	PRAYER(Skill.PRAYER, "skill_prayer.json"),
	RUNECRAFT(Skill.RUNECRAFT, "skill_runecraft.json"),
	SMITHING(Skill.SMITHING, "skill_smithing.json"),
	THIEVING(Skill.THIEVING, "skill_thieving.json"),
	WOODCUTTING(Skill.WOODCUTTING, "skill_woodcutting.json");

	private Skill skill;
	private String dataFile;

	CalculatorType(Skill skill, String dataFile)
	{
		this.skill = skill;
		this.dataFile = dataFile;
	}

	Skill getSkill()
	{
		return this.skill;
	}

	String getDataFile()
	{
		return this.dataFile;
	}
}