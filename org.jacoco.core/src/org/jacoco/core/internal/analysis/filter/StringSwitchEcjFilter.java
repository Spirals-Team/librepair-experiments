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
package org.jacoco.core.internal.analysis.filter;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

public final class StringSwitchEcjFilter implements IFilter {

	public void filter(final MethodNode methodNode,
			final IFilterContext context, final IFilterOutput output) {
		final Matcher matcher = new Matcher();
		for (AbstractInsnNode i = methodNode.instructions
				.getFirst(); i != null; i = i.getNext()) {
			matcher.match(i, output);
		}
	}

	private static class Matcher extends AbstractMatcher {
		public void match(final AbstractInsnNode start,
				final IFilterOutput output) {
			cursor = start;

			nextIsVar(Opcodes.ASTORE, "s");
			nextIsInvokeVirtual("java/lang/String", "hashCode");
			nextIsSwitch();
			if (cursor == null) {
				return;
			}
			final int hashCodes;
			final LabelNode defaultLabel;
			final AbstractInsnNode s = cursor;
			if (cursor.getOpcode() == Opcodes.LOOKUPSWITCH) {
				final LookupSwitchInsnNode lookupSwitch = (LookupSwitchInsnNode) cursor;
				defaultLabel = lookupSwitch.dflt;
				hashCodes = lookupSwitch.labels.size();
			} else {
				final TableSwitchInsnNode tableSwitch = (TableSwitchInsnNode) cursor;
				defaultLabel = tableSwitch.dflt;
				hashCodes = tableSwitch.labels.size();
			}

			final List<AbstractInsnNode> ignore = new ArrayList<AbstractInsnNode>();
			for (int i = 0; i < hashCodes; i++) {
				while (true) {
					nextIsVar(Opcodes.ALOAD, "s");
					nextIs(Opcodes.LDC);
					nextIsInvokeVirtual("java/lang/String", "equals");
					// jump to next comparison or case
					nextIs(Opcodes.IFNE);
					ignore.add(cursor);
					// jump to default
					nextIs(Opcodes.GOTO);
					if (cursor == null) {
						return;
					}

					// FIXME wrong
					if ((((JumpInsnNode) cursor)).label == defaultLabel) {
						// end of comparisons for same hashCode
						break;
					}
				}
			}

			for (AbstractInsnNode i : ignore) {
				output.ignore(i, i);
			}
			output.ignore(s, s);
		}
	}

}
