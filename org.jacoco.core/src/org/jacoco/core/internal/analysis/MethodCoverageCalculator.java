/*******************************************************************************
 * Copyright (c) 2009, 2018 Mountainminds GmbH & Co. KG and Contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Evgeny Mandrikov - initial API and implementation
 *    
 *******************************************************************************/
package org.jacoco.core.internal.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.internal.analysis.filter.IFilterOutput;
import org.jacoco.core.internal.flow.Instruction;
import org.objectweb.asm.tree.AbstractInsnNode;

/**
 * Calculates the filtered coverage of a single method.
 */
class MethodCoverageCalculator implements IFilterOutput {

	private final Map<AbstractInsnNode, Instruction> instructions;

	private final Map<AbstractInsnNode, Instruction> filtered;

	MethodCoverageCalculator(
			final Map<AbstractInsnNode, Instruction> instructions) {
		this.instructions = instructions;
		this.filtered = new HashMap<AbstractInsnNode, Instruction>(
				instructions);
	}

	void calculateCoverage(final MethodCoverageImpl coverage) {

		for (final Instruction insn : filtered.values()) {
			final int total = insn.getBranches();
			final int covered = insn.getCoveredBranches();
			final ICounter instrCounter = covered == 0 ? CounterImpl.COUNTER_1_0
					: CounterImpl.COUNTER_0_1;
			final ICounter branchCounter = total > 1
					? CounterImpl.getInstance(total - covered, covered)
					: CounterImpl.COUNTER_0_0;
			coverage.increment(instrCounter, branchCounter, insn.getLine());
		}

		coverage.incrementMethodCounter();
	}

	// === IFilterOutput API ===

	public void ignore(final AbstractInsnNode fromInclusive,
			final AbstractInsnNode toInclusive) {
		for (AbstractInsnNode i = fromInclusive; i != toInclusive; i = i
				.getNext()) {
			filtered.remove(i);
		}
		filtered.remove(toInclusive);
	}

	public void merge(final AbstractInsnNode from, final AbstractInsnNode to) {
		final Instruction insn1 = getInstruction(from);
		final Instruction insn2 = getInstruction(to);
		filtered.remove(from);
		filtered.put(to, insn1.merge(insn2));
	}

	public void replaceBranches(final AbstractInsnNode source,
			final Set<AbstractInsnNode> newTargets) {
		final Instruction r = new Instruction(getInstruction(source).getLine());
		int i = 0;
		for (final AbstractInsnNode target : newTargets) {
			r.addBranch(getInstruction(target), i++);
		}
		filtered.put(source, r);
	}

	private Instruction getInstruction(final AbstractInsnNode key) {
		final Instruction i = filtered.get(key);
		return i == null ? instructions.get(key) : i;
	}

}
