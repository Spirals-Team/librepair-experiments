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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;

import java.util.ArrayList;
import java.util.List;

public final class KotlinWhenStringFilter implements IFilter {

	public void filter(final MethodNode methodNode,
			final IFilterContext context, final IFilterOutput output) {
		final Matcher matcher = new Matcher();
		for (AbstractInsnNode i = methodNode.instructions
				.getFirst(); i != null; i = i.getNext()) {
			matcher.match(i, output);
		}
	}

	/**
	 * <pre>
	 * 	 private final int whenString(java.lang.String);
	 * 	 descriptor: (Ljava/lang/String;)I
	 * 	 flags: ACC_PRIVATE, ACC_FINAL
	 * 	 Code:
	 * 	 stack=2, locals=3, args_size=2
	 * 	 0: aload_1
	 *
	 * 	 1: astore_2
	 * 	 2: aload_2
	 * 	 3: invokevirtual #51                 // Method java/lang/String.hashCode:()I
	 * 	 6: tableswitch   { // 97 to 98
	 * 	 97: 28
	 * 	 98: 52
	 * 	 default: 92
	 * 	 }
	 *
	 * 	 28: aload_2
	 * 	 29: ldc           #53                 // String a
	 * 	 31: invokevirtual #57                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
	 * 	 34: ifeq          40
	 * 	 37: goto          76
	 * 	 40: aload_2
	 * 	 41: ldc           #59                 // String a
	 * 	 43: invokevirtual #57                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
	 * 	 46: ifeq          92
	 * 	 49: goto          84
	 *
	 * 	 52: aload_2
	 * 	 53: ldc           #61                 // String b
	 * 	 55: invokevirtual #57                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
	 * 	 58: ifeq          64
	 * 	 61: goto          80
	 * 	 64: aload_2
	 * 	 65: ldc           #63                 // String b
	 * 	 67: invokevirtual #57                 // Method java/lang/String.equals:(Ljava/lang/Object;)Z
	 * 	 70: ifeq          92
	 * 	 73: goto          88
	 *
	 * 	 76: iconst_1
	 * 	 77: goto          93
	 * 	 80: iconst_2
	 * 	 81: goto          93
	 * 	 84: iconst_3
	 * 	 85: goto          93
	 * 	 88: iconst_4
	 * 	 89: goto          93
	 * 	 92: iconst_5
	 * 	 93: ireturn
	 * </pre>
	 */
	private static class Matcher extends AbstractMatcher {
		public void match(final AbstractInsnNode start,
				final IFilterOutput output) {
			cursor = start;

			nextIsVar(Opcodes.ASTORE, "s");
			nextIsVar(Opcodes.ALOAD, "s");
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
					// jump to next comparison or default case
					nextIs(Opcodes.IFEQ);
					if (cursor == null) {
						return;
					}
					final JumpInsnNode jump = (JumpInsnNode) cursor;
					ignore.add(jump);
					// jump to case
					nextIs(Opcodes.GOTO);

					if (jump.label == defaultLabel) {
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
